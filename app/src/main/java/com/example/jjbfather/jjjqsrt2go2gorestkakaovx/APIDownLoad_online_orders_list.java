package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by BCS_RTBS_JJFATHER on 2017-06-22.
 */
public class APIDownLoad_online_orders_list extends AsyncTask {
    final String TAG = "onlineorders";

    String idx = "";
    String salesCode = "";
    String customerName = "";
    String deliverytakeaway = "";
    String TotalAmount = "";
    String deliveryStatus = "";
    String takeawayStatus = "";
    String saleDate = "";
    String status = "";
    String customerOrderNumber = "";
    // jihun 0704
    String arrivalconfirmyn = "";
    // jihun 210127
    String OnlineOrderType = "";
    // jihun 210318
    String CurbsideYN = "";
    String Pickupdone = "";

    // 10112023
    String orderfrom = "";
    String salescodethirdparty = "";

    /**********************************************************/

    String mDataCollection = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;

    public APIDownLoad_online_orders_list(String paramFromDate, String paramToDate, String paramSchTxt, String paramSchDeliTake) {
        mStrUrl = GlobalMemberValues.API_WEBORDER_URL2 + "?" +
                "sidx=" + GlobalMemberValues.STORE_INDEX +
                "&fromDate=" + paramFromDate + "&toDate=" + paramToDate +
                "&schTxt=" + paramSchTxt +
                "&schDeliTake=" + paramSchDeliTake +
                "&schOnlinetype=" + SaleHistoryList_web.mSearchOnlineType;          // 12092022
        GlobalMemberValues.logWrite("weborderslog", "url : " + mStrUrl + "\n");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (SaleHistoryList_web.mCollectionOrdersArrList != null) {
            SaleHistoryList_web.mCollectionOrdersArrList.clear();
        }

        //GlobalMemberValues.logWrite(TAG, "mStrUrl : " + mStrUrl + "\n");
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
                        if (tagName.equals("CustomerName")) {
                            customerName = xpp.getText();
                        }
                        if (tagName.equals("Deliverytakeaway")) {
                            deliverytakeaway = xpp.getText();
                        }
                        if (tagName.equals("TotalAmount")) {
                            TotalAmount = xpp.getText();
                        }
                        if (tagName.equals("DeliveryStatus")) {
                            deliveryStatus = xpp.getText();
                        }
                        if (tagName.equals("TakeawayStatus")) {
                            takeawayStatus = xpp.getText();
                        }
                        if (tagName.equals("SaleDate")) {
                            saleDate = xpp.getText();
                        }
                        if (tagName.equals("Status")) {
                            status = xpp.getText();
                        }
                        if (tagName.equals("CustomerOrderNumber")) {
                            customerOrderNumber = xpp.getText();
                        }
                        if (tagName.equals("Arrivalconfirmyn")) {
                            arrivalconfirmyn = xpp.getText();
                        }
                        if (tagName.equals("OnlineOrderType")) {
                            OnlineOrderType = xpp.getText();
                        }
                        if (tagName.equals("CurbsideYN")) {
                            CurbsideYN = xpp.getText();
                        }
                        if (tagName.equals("CurbsideYN")) {
                            CurbsideYN = xpp.getText();
                        }
                        if (tagName.equals("Pickupdone")) {
                            Pickupdone = xpp.getText();
                        }

                        // 10112023
                        if (tagName.equals("Orderfrom")) {
                            orderfrom = xpp.getText();
                        }
                        if (tagName.equals("Salescodethirdparty")) {
                            salescodethirdparty = xpp.getText();
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        String mPickupType = "HERE";
                        switch (deliverytakeaway) {
                            case "H" : {
                                mPickupType = "HERE";
                                break;
                            }
                            case "T" : {
                                mPickupType = "TO GO";
                                break;
                            }
                            case "D" : {
                                mPickupType = "DELIVERY";
                                break;
                            }
                        }

                        mDataCollection = "";
                        mDataCollection = customerOrderNumber + "-JJJ-" + mPickupType + "-JJJ-" + salesCode + "-JJJ-" + TotalAmount + "-JJJ-" + saleDate + "-JJJ-" + customerName +
                                "-JJJ-" + idx + "-JJJ-" + status + "-JJJ-" + deliveryStatus + "-JJJ-" + takeawayStatus + "-JJJ-" + arrivalconfirmyn + "-JJJ-" + OnlineOrderType +
                                "-JJJ-" + CurbsideYN + "-JJJ-" + Pickupdone + "-JJJ-" +
                                // 10112023
                                orderfrom + "-JJJ-" + salescodethirdparty + "-JJJ-" +
                                "END";

                        SaleHistoryList_web.mCollectionOrdersArrList.add(mDataCollection);


                        GlobalMemberValues.logWrite(TAG, "mDataCollection : " + mDataCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        salesCode = "";
                        customerName = "";
                        deliverytakeaway = "";
                        TotalAmount = "";
                        deliveryStatus = "";
                        takeawayStatus = "";
                        saleDate = "";
                        status = "";
                        customerOrderNumber = "";
                        arrivalconfirmyn = "";
                        OnlineOrderType = "";
                        CurbsideYN = "";
                        Pickupdone = "";

                        // 10112023
                        orderfrom = "";
                        salescodethirdparty = "";
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
