package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by BCS_RTBS_JJFATHER on 2017-06-22.
 */
public class APIDownLoad_online_orders_realtime extends AsyncTask {
    final String TAG = "onlineorders";

    String idx = "";
    String salesCode = "";
    String customerId = "";
    String customerName = "";
    String customerPhone = "";
    String customerEmail = "";
    String customerAddr1 = "";
    String customerAddr2 = "";
    String customerCity = "";
    String customerState = "";
    String customerZip = "";
    String deliveryday = "";
    String deliverytime = "";
    String deliverydate = "";
    String customermemo = "";
    String couponnumber = "";
    String deliverytakeaway = "";
    String discount = "";
    String discountTxt = "";
    String giftcardNumberUsed = "";
    String giftcardPriceUsed = "";
    String cardPriceUsed = "";
    String cashPriceUsed = "";
    String pointPriceUsed = "";
    String deliveryStatus = "";
    String takeawayStatus = "";
    String receiptJson = "";
    String saleDate = "";
    String salesItems = "";
    String status = "";
    /**********************************************************/

    String mDataCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEBORDER_URL + "?" +
            "sidx=" + GlobalMemberValues.STORE_INDEX +
            "&fromDate=&toDate=" +
            "&schTxt=" +
            "&schDeliTake=ALL";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;

    @Override
    protected Object doInBackground(Object[] params) {
        GlobalMemberValues.logWrite(TAG, "mStrUrl : " + mStrUrl + "\n");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite(TAG, "mStrUrl : " + mStrUrl + "\n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            //member2 는 테이블 데이터를 삭제하지 않는다.
            //sqlQueryVecIns.add("delete from " + mDbTableName);

            int tempUidCnt = 0;

            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = true;
                    }
                    if (tagName.equals("Data")) {
                        isReturnCode = false;
                        isDataTag = true;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isReturnCode && tagName.equals("ReturnCode")) {
                        mApiReturnCode = xpp.getText();
                        if (mApiReturnCode.equals("00")) {
                            isPossible = true;
                            GlobalMemberValues.logWrite(TAG, "데이터 있음" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite(TAG, "데이터 없음" + "\n");
                        }
                        GlobalMemberValues.logWrite(TAG, "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite(TAG, "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("SalesCode")) {
                            salesCode = xpp.getText();
                        }
                        if (tagName.equals("CustomerId")) {
                            customerId = xpp.getText();
                        }
                        if (tagName.equals("CustomerName")) {
                            customerName = xpp.getText();
                        }
                        if (tagName.equals("CustomerPhone")) {
                            customerPhone = xpp.getText();
                        }
                        if (tagName.equals("CustomerEmail")) {
                            customerEmail = xpp.getText();
                        }
                        if (tagName.equals("CustomerAddr1")) {
                            customerAddr1 = xpp.getText();
                        }
                        if (tagName.equals("CustomerAddr2")) {
                            customerAddr2 = xpp.getText();
                        }
                        if (tagName.equals("CustomerCity")) {
                            customerCity = xpp.getText();
                        }
                        if (tagName.equals("CustomerState")) {
                            customerState = xpp.getText();
                        }
                        if (tagName.equals("CustomerZip")) {
                            customerZip = xpp.getText();
                        }
                        if (tagName.equals("Deliveryday")) {
                            deliveryday = xpp.getText();
                        }
                        if (tagName.equals("Deliverytime")) {
                            deliverytime = xpp.getText();
                        }
                        if (tagName.equals("Deliverydate")) {
                            deliverydate = xpp.getText();
                        }
                        if (tagName.equals("Customermemo")) {
                            customermemo = xpp.getText();
                        }
                        if (tagName.equals("Couponnumber")) {
                            couponnumber = xpp.getText();
                        }
                        if (tagName.equals("Deliverytakeaway")) {
                            deliverytakeaway = xpp.getText();
                        }
                        if (tagName.equals("Discount")) {
                            discount = xpp.getText();
                        }
                        if (tagName.equals("DiscountTxt")) {
                            discountTxt = xpp.getText();
                        }
                        if (tagName.equals("GiftcardNumberUsed")) {
                            giftcardNumberUsed = xpp.getText();
                        }
                        if (tagName.equals("GiftcardPriceUsed")) {
                            giftcardPriceUsed = xpp.getText();
                        }
                        if (tagName.equals("CardPriceUsed")) {
                            cardPriceUsed = xpp.getText();
                        }
                        if (tagName.equals("CashPriceUsed")) {
                            cashPriceUsed = xpp.getText();
                        }
                        if (tagName.equals("PointPriceUsed")) {
                            pointPriceUsed = xpp.getText();
                        }
                        if (tagName.equals("DeliveryStatus")) {
                            deliveryStatus = xpp.getText();
                        }
                        if (tagName.equals("TakeawayStatus")) {
                            takeawayStatus = xpp.getText();
                        }
                        if (tagName.equals("ReceiptJson")) {
                            receiptJson = xpp.getText();
                        }
                        if (tagName.equals("SaleDate")) {
                            saleDate = xpp.getText();
                        }
                        if (tagName.equals("Status")) {
                            status = xpp.getText();
                        }
                        if (tagName.equals("SalesItems")) {
                            salesItems = xpp.getText();
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        // LinkedHashMap 에 담을 값
                        String[] tempStringArr = {
                                idx, salesCode, customerId, customerName, customerPhone, customerEmail,
                                customerAddr1, customerAddr2, customerCity, customerState, customerZip,
                                deliveryday, deliverytime, deliverydate,
                                customermemo, couponnumber, deliverytakeaway,
                                discount, discountTxt, giftcardNumberUsed,
                                giftcardPriceUsed, cardPriceUsed, cashPriceUsed, pointPriceUsed,
                                deliveryStatus, takeawayStatus, receiptJson, saleDate, status,
                                salesItems
                        };
                        TemporaryWebOrders temporaryWebOrders = new TemporaryWebOrders(tempStringArr);
                        RealTimeWebOrders.mCollectionOrdersArrList.add(temporaryWebOrders);

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mDataCollection = "[" + idx + "], [" + salesCode + "], [" + customerId + "], [" +
                                customerName + "], [" + customerPhone + "], [" + customerEmail + "], [" + customerAddr1 + "], [" +
                                customerAddr2 + "], [" + customerCity + "], [" + customerState + "], [" + customerZip + "], [" +
                                deliveryday + "], [" + deliverytime + "], [" + deliverydate + "], [" + customermemo + "], [" +
                                couponnumber + "], [" + deliverytakeaway + "], [" + discount + "], [" + discountTxt + "], [" +
                                giftcardNumberUsed + "], [" + giftcardPriceUsed + "], [" + cardPriceUsed + "], [" + cashPriceUsed + "], [" +
                                pointPriceUsed + "], [" + deliveryStatus + "], [" + takeawayStatus + "], [" + receiptJson + "], [" +
                                saleDate + "], [" + status + "], [" + salesItems + "]";

                        GlobalMemberValues.logWrite(TAG, "mDataCollection : " + mDataCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        salesCode = "";
                        customerId = "";
                        customerName = "";
                        customerPhone = "";
                        customerEmail = "";
                        customerAddr1 = "";
                        customerAddr2 = "";
                        customerCity = "";
                        customerState = "";
                        customerZip = "";
                        deliveryday = "";
                        deliverytime = "";
                        deliverydate = "";
                        customermemo = "";
                        couponnumber = "";
                        deliverytakeaway = "";
                        discount = "";
                        discountTxt = "";
                        giftcardNumberUsed = "";
                        giftcardPriceUsed = "";
                        cardPriceUsed = "";
                        cashPriceUsed = "";
                        pointPriceUsed = "";
                        deliveryStatus = "";
                        takeawayStatus = "";
                        receiptJson = "";
                        saleDate = "";
                        status = "";
                        salesItems = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG, "예외발생 : " + e.getMessage());
        }

        return null;
    }
}
