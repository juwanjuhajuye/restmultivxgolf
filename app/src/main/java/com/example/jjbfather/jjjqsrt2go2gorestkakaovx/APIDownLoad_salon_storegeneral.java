package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-04.
 */
public class APIDownLoad_salon_storegeneral extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storegeneral";

    String idx = "";
    String sidx = "";
    String managerpwd = "";
    String tax1 = "";
    String tax2 = "";
    String tax3 = "";
    String tax4 = "";
    String tax5 = "";
    String rspuseyn = "";
    String rsprate = "";
    String categorysu = "";
    String delyn = "";
    String wdate = "";
    String mdate = "";

    String mstart = "";
    String mend = "";
    String astart = "";
    String aend = "";
    String estart = "";
    String eend = "";
    String nstart = "";
    String nend = "";

    String takeawayyn = "";
    String deliveryyn = "";
    String deliverycharge = "";
    String deliveryfree = "";
    String pickupcharge = "";

    String picktype_here = "";
    String picktype_togo = "";
    String picktype_delivery = "";

    String phoneorder = "";
    String kitchenprintsu = "";

    String minpayforsign = "";

    String storetype = "";
    String franchiseyn = "";

    String ordercompletesmsyn = "";
    String ordercompletesmsremain = "";
    String ordercompletesmsterm = "";

    String togotaxfreeyn = "";
    String togotaxfreetype = "";
    String heretaxfreeyn = "";
    String heretaxfreetype = "";
    String deliverytaxfreeyn = "";
    String deliverytaxfreetype = "";
    String dineintaxfreeyn = "";
    String dineintaxfreetype = "";
    String mobiletaxfreeyn = "";
    String mobiletaxfreetype = "";
    String taxexemptbytotalyn = "";

    String modifierviewtype = "";

    String posdualdpimg = "";
    String posdualdptype = "";

    String posreceiptlogimg = "";

    String scaleuseyn = "";
    String scaleunit = "";

    String scaleminweight = "";
    String scalemaxweight = "";

    String postype = "";

    String scaledecimaltwiceyn = "";

    String webpayuseyn = "";
    String curbsidepickupyn = "";
    String sideorderyn = "";

    String gratuityuseyn = "";
    String gratuitytype = "";
    String gratuityvalue = "";
    String gratuitycustomercount = "";

    String cashoutreportitems = "";

    String divideruseyn = "";

    String labelprinteruse = "";

    String managerpwdnz = "";

    String suggestiontiptype = "";

    String commongratuitytype = "";

    String multistationsyncuseyn = "";

    String addpaytype = "";
    String addpaymoneytype = "";
    String addpaymoney = "";
    String addpaytaxtype = "";
    String addpayname = "";

    String scaleautoweighyn = "";

    String wmuseyn = "";
    String wmapiip = "";

    // 05112023
    String wmoptionstsr = "";
    String addpaycustomerselectyn = "";

    // 06212023
    String addpayitempriceshowyn = "";
    String cashinoutstartendcashyn = "";

    // 08012023
    String gratuitydelyn = "";
    String crmuseyn = "";
    String cashdcshowonreceiptyn = "";
    String cashdctaxshowyn = "";

    // 03252024
    String torderuseyn = "";
    String torderkey = "";

    // 04302024
    String qsronrestaurantyn = "";


    // 05302024
    String torderapikey = "";
    String torderpartnerid = "";
    String torderapiurl = "";


    String totaloptionitem = "";
    /**********************************************************/

    String mInsertSqlQuery = "";
    String mUpdateSqlQuery = "";
    String mQueryCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEB_URL + "?" +
            "RequestSqlType=" + mDbTableName + "&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=" + GlobalMemberValues.API_WEB_DB_XML_TYPE;

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();
    public Vector<String> sqlQueryVecIns = new Vector<String>();

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            sqlQueryVecIns.add("delete from " + mDbTableName);

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
                            GlobalMemberValues.logWrite("salon_storegeneral", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storegeneral", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storegeneral", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storegeneral", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("managerpwd")) {
                            managerpwd = xpp.getText();
                        }
                        if (tagName.equals("tax1")) {
                            tax1 = xpp.getText();
                        }
                        if (tagName.equals("tax2")) {
                            tax2 = xpp.getText();
                        }
                        if (tagName.equals("tax3")) {
                            tax3 = xpp.getText();
                        }
                        if (tagName.equals("tax4")) {
                            tax4 = xpp.getText();
                        }
                        if (tagName.equals("tax5")) {
                            tax5 = xpp.getText();
                        }
                        if (tagName.equals("rspuseyn")) {
                            rspuseyn = xpp.getText();
                        }
                        if (tagName.equals("rsprate")) {
                            rsprate = xpp.getText();
                        }
                        if (tagName.equals("categorysu")) {
                            categorysu = xpp.getText();
                        }
                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }
                        if (tagName.equals("mdate")) {
                            mdate = xpp.getText();
                        }

                        if (tagName.equals("mstart")) {
                            mstart = xpp.getText();
                        }
                        if (tagName.equals("mend")) {
                            mend = xpp.getText();
                        }
                        if (tagName.equals("astart")) {
                            astart = xpp.getText();
                        }
                        if (tagName.equals("aend")) {
                            aend = xpp.getText();
                        }
                        if (tagName.equals("estart")) {
                            estart = xpp.getText();
                        }
                        if (tagName.equals("eend")) {
                            eend = xpp.getText();
                        }
                        if (tagName.equals("nstart")) {
                            nstart = xpp.getText();
                        }
                        if (tagName.equals("nend")) {
                            nend = xpp.getText();
                        }

                        if (tagName.equals("takeawayyn")) {
                            takeawayyn = xpp.getText();
                        }
                        if (tagName.equals("deliveryyn")) {
                            deliveryyn = xpp.getText();
                        }
                        if (tagName.equals("deliverycharge")) {
                            deliverycharge = xpp.getText();
                        }
                        if (tagName.equals("deliveryfree")) {
                            deliveryfree = xpp.getText();
                        }
                        if (tagName.equals("pickupcharge")) {
                            pickupcharge = xpp.getText();
                        }
                        if (tagName.equals("picktype_here")) {
                            picktype_here = xpp.getText();
                        }
                        if (tagName.equals("picktype_togo")) {
                            picktype_togo = xpp.getText();
                        }
                        if (tagName.equals("picktype_delivery")) {
                            picktype_delivery = xpp.getText();
                        }
                        if (tagName.equals("phoneorder")) {
                            phoneorder = xpp.getText();
                        }
                        if (tagName.equals("kitchenprintsu")) {
                            kitchenprintsu = xpp.getText();
                        }
                        if (tagName.equals("minpayforsign")) {
                            minpayforsign = xpp.getText();
                        }
                        if (tagName.equals("storetype")) {
                            storetype = xpp.getText();
                        }
                        if (tagName.equals("franchiseyn")) {
                            franchiseyn = xpp.getText();
                        }
                        if (tagName.equals("ordercompletesmsyn")) {
                            ordercompletesmsyn = xpp.getText();
                        }
                        if (tagName.equals("ordercompletesmsremain")) {
                            ordercompletesmsremain = xpp.getText();
                        }
                        if (tagName.equals("ordercompletesmsterm")) {
                            ordercompletesmsterm = xpp.getText();
                        }

                        if (tagName.equals("togotaxfreeyn")) {
                            togotaxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("togotaxfreetype")) {
                            togotaxfreetype = xpp.getText();
                        }
                        if (tagName.equals("heretaxfreeyn")) {
                            heretaxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("heretaxfreetype")) {
                            heretaxfreetype = xpp.getText();
                        }
                        if (tagName.equals("deliverytaxfreeyn")) {
                            deliverytaxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("deliverytaxfreetype")) {
                            deliverytaxfreetype = xpp.getText();
                        }
                        if (tagName.equals("dineintaxfreeyn")) {
                            dineintaxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("dineintaxfreetype")) {
                            dineintaxfreetype = xpp.getText();
                        }
                        if (tagName.equals("mobiletaxfreeyn")) {
                            mobiletaxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("mobiletaxfreetype")) {
                            mobiletaxfreetype = xpp.getText();
                        }
                        if (tagName.equals("taxexemptbytotalyn")) {
                            taxexemptbytotalyn = xpp.getText();
                        }
                        if (tagName.equals("modifierviewtype")) {
                            modifierviewtype = xpp.getText();
                        }
                        if (tagName.equals("posdualdpimg")) {
                            posdualdpimg = xpp.getText();
                        }
                        if (tagName.equals("posdualdptype")) {
                            posdualdptype = xpp.getText();
                        }
                        if (tagName.equals("posreceiptlogimg")) {
                            posreceiptlogimg = xpp.getText();
                        }
                        if (tagName.equals("scaleuseyn")) {
                            scaleuseyn = xpp.getText();
                        }
                        if (tagName.equals("scaleunit")) {
                            scaleunit = xpp.getText();
                        }
                        if (tagName.equals("scaleminweight")) {
                            scaleminweight = xpp.getText();
                        }
                        if (tagName.equals("scalemaxweight")) {
                            scalemaxweight = xpp.getText();
                        }
                        if (tagName.equals("postype")) {
                            postype = xpp.getText();
                        }
                        if (tagName.equals("scaledecimaltwiceyn")) {
                            scaledecimaltwiceyn = xpp.getText();
                        }
                        if (tagName.equals("webpayuseyn")) {
                            webpayuseyn = xpp.getText();
                        }
                        if (tagName.equals("curbsidepickupyn")) {
                            curbsidepickupyn = xpp.getText();
                        }
                        if (tagName.equals("sideorderyn")) {
                            sideorderyn = xpp.getText();
                        }
                        if (tagName.equals("gratuityuseyn")) {
                            gratuityuseyn = xpp.getText();
                        }
                        if (tagName.equals("gratuitytype")) {
                            gratuitytype = xpp.getText();
                        }
                        if (tagName.equals("gratuityvalue")) {
                            gratuityvalue = xpp.getText();
                        }
                        if (tagName.equals("gratuitycustomercount")) {
                            gratuitycustomercount = xpp.getText();
                        }
                        if (tagName.equals("cashoutreportitems")) {
                            cashoutreportitems = xpp.getText();
                        }
                        if (tagName.equals("divideruseyn")) {
                            divideruseyn = xpp.getText();
                        }
                        if (tagName.equals("labelprinteruse")) {
                            labelprinteruse = xpp.getText();
                        }
                        if (tagName.equals("managerpwdnz")) {
                            managerpwdnz = xpp.getText();
                        }
                        if (tagName.equals("suggestiontiptype")) {
                            suggestiontiptype = xpp.getText();
                        }
                        if (tagName.equals("commongratuitytype")) {
                            commongratuitytype = xpp.getText();
                        }
                        if (tagName.equals("multistationsyncuseyn")) {
                            multistationsyncuseyn = xpp.getText();
                        }

                        if (tagName.equals("addpaytype")) {
                            addpaytype = xpp.getText();
                        }
                        if (tagName.equals("addpaymoneytype")) {
                            addpaymoneytype = xpp.getText();
                        }
                        if (tagName.equals("addpaymoney")) {
                            addpaymoney = xpp.getText();
                        }
                        if (tagName.equals("addpaytaxtype")) {
                            addpaytaxtype = xpp.getText();
                        }
                        if (tagName.equals("addpayname")) {
                            addpayname = xpp.getText();
                        }
                        if (tagName.equals("scaleautoweighyn")) {
                            scaleautoweighyn = xpp.getText();
                        }

                        if (tagName.equals("wmuseyn")) {
                            wmuseyn = xpp.getText();
                        }
                        if (tagName.equals("wmapiip")) {
                            wmapiip = xpp.getText();
                        }

                        // 05112023
                        if (tagName.equals("wmoptionstsr")) {
                            wmoptionstsr = xpp.getText();
                        }
                        if (tagName.equals("addpaycustomerselectyn")) {
                            addpaycustomerselectyn = xpp.getText();
                        }

                        // 06212023
                        if (tagName.equals("addpayitempriceshowyn")) {
                            addpayitempriceshowyn = xpp.getText();
                        }
                        if (tagName.equals("cashinoutstartendcashyn")) {
                            cashinoutstartendcashyn = xpp.getText();
                        }


                        // 08012023
                        if (tagName.equals("gratuitydelyn")) {
                            gratuitydelyn = xpp.getText();
                        }
                        if (tagName.equals("crmuseyn")) {
                            crmuseyn = xpp.getText();
                        }
                        if (tagName.equals("cashdcshowonreceiptyn")) {
                            cashdcshowonreceiptyn = xpp.getText();
                        }
                        if (tagName.equals("cashdctaxshowyn")) {
                            cashdctaxshowyn = xpp.getText();
                        }

                        // 03252024
                        if (tagName.equals("torderuseyn")) {
                            torderuseyn = xpp.getText();
                        }
                        if (tagName.equals("torderkey")) {
                            torderkey = xpp.getText();
                        }


                        // 04302024
                        if (tagName.equals("qsronrestaurantyn")) {
                            qsronrestaurantyn = xpp.getText();
                        }

                        // 05302024
                        if (tagName.equals("torderapikey")) {
                            torderapikey = xpp.getText();
                        }
                        if (tagName.equals("torderpartnerid")) {
                            torderpartnerid = xpp.getText();
                        }
                        if (tagName.equals("torderapiurl")) {
                            torderapiurl = xpp.getText();
                        }



                        if (tagName.equals("totaloptionitem")) {
                            totaloptionitem = xpp.getText();
                        }
                    }



                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        // 등록쿼리 조합하기
                        mInsertSqlQuery = "insert into " + mDbTableName +
                                " ( " +
                                " idx, sidx, managerpwd, tax1, tax2, tax3, tax4, tax5, " +
                                " rspuseyn, rsprate, categorysu, delyn, wdate, mdate, " +
                                " mstart, mend, astart, aend, estart, eend, nstart, nend, " +
                                " takeawayyn, deliveryyn, deliverycharge, deliveryfree, pickupcharge, " +
                                " picktype_here, picktype_togo, picktype_delivery, phoneorder, kitchenprintsu, minpayforsign, " +
                                " storetype, franchiseyn, " +
                                " ordercompletesmsyn, ordercompletesmsremain, ordercompletesmsterm, " +
                                " togotaxfreeyn, togotaxfreetype, heretaxfreeyn, heretaxfreetype, deliverytaxfreeyn, deliverytaxfreetype, " +
                                " dineintaxfreeyn, dineintaxfreetype, mobiletaxfreeyn, mobiletaxfreetype, taxexemptbytotalyn, modifierviewtype, posdualdpimg, posdualdptype, posreceiptlogimg, " +
                                " scaleuseyn, scaleunit, scaleminweight, scalemaxweight, postype, scaledecimaltwiceyn, webpayuseyn, " +
                                " curbsidepickupyn, sideorderyn, gratuityuseyn, gratuitytype, gratuityvalue, gratuitycustomercount, cashoutreportitems, divideruseyn, labelprinteruse, managerpwdnz, " +
                                " suggestiontiptype, commongratuitytype, multistationsyncuseyn, " +
                                " addpaytype, addpaymoneytype, addpaymoney, addpaytaxtype, addpayname, scaleautoweighyn, " +
                                " wmuseyn, wmapiip, wmoptionstsr, addpaycustomerselectyn, " +
                                // 06212023
                                " addpayitempriceshowyn, cashinoutstartendcashyn, " +
                                // 08012023
                                " gratuitydelyn, crmuseyn, cashdcshowonreceiptyn, cashdctaxshowyn, " +
                                // 03252024
                                " torderuseyn, torderkey, qsronrestaurantyn,  " +
                                // 05302024
                                " torderapikey, torderpartnerid, torderapiurl " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(managerpwd, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tax1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tax2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tax3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tax4, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tax5, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(rspuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(rsprate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(categorysu, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(mstart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mend, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(astart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aend, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(estart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(eend, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(nstart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(nend, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(takeawayyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deliveryyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deliverycharge, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deliveryfree, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pickupcharge, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(picktype_here, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(picktype_togo, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(picktype_delivery, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(phoneorder, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintsu, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(minpayforsign, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(storetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(franchiseyn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsremain, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsterm, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(togotaxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(togotaxfreetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(heretaxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(heretaxfreetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deliverytaxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deliverytaxfreetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(dineintaxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(dineintaxfreetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mobiletaxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mobiletaxfreetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxexemptbytotalyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(modifierviewtype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(posdualdpimg, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(posdualdptype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(posreceiptlogimg, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaleuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaleunit, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaleminweight, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scalemaxweight, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(postype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaledecimaltwiceyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(webpayuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(curbsidepickupyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sideorderyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gratuityuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gratuitytype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gratuityvalue, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gratuitycustomercount, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cashoutreportitems, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(divideruseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(labelprinteruse, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(managerpwdnz, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(suggestiontiptype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(commongratuitytype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(multistationsyncuseyn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpaytype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpaymoneytype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpaymoney, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpaytaxtype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpayname, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaleautoweighyn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(wmuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wmapiip, 0) + "', " +

                                // 05112023
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wmoptionstsr, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpaycustomerselectyn, 0) + "', " +

                                // 06212023
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addpayitempriceshowyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cashinoutstartendcashyn, 0) + "', " +

                                // 08012023
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gratuitydelyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(crmuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cashdcshowonreceiptyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cashdctaxshowyn, 0) + "', " +

                                // 03252024
                                "'" + GlobalMemberValues.getDBTextAfterChecked(torderuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(torderkey, 0) + "', " +

                                // 04302024
                                "'" + GlobalMemberValues.getDBTextAfterChecked(qsronrestaurantyn, 0) + "', " +

                                // 05302024
                                "'" + GlobalMemberValues.getDBTextAfterChecked(torderapikey, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(torderpartnerid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(torderapiurl, 0) + "' " +

                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " managerpwd = '" + GlobalMemberValues.getDBTextAfterChecked(managerpwd, 0) + "', " +
                                " tax1 = '" + GlobalMemberValues.getDBTextAfterChecked(tax1, 0) + "', " +
                                " tax2 = '" + GlobalMemberValues.getDBTextAfterChecked(tax2, 0) + "', " +
                                " tax3 = '" + GlobalMemberValues.getDBTextAfterChecked(tax3, 0) + "', " +
                                " tax4 = '" + GlobalMemberValues.getDBTextAfterChecked(tax4, 0) + "', " +
                                " tax5 = '" + GlobalMemberValues.getDBTextAfterChecked(tax5, 0) + "', " +
                                " rspuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(rspuseyn, 0) + "', " +
                                " rsprate = '" + GlobalMemberValues.getDBTextAfterChecked(rsprate, 0) + "', " +
                                " categorysu = '" + GlobalMemberValues.getDBTextAfterChecked(categorysu, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +

                                " mstart = '" + GlobalMemberValues.getDBTextAfterChecked(mstart, 0) + "', " +
                                " mend = '" + GlobalMemberValues.getDBTextAfterChecked(mend, 0) + "', " +
                                " astart = '" + GlobalMemberValues.getDBTextAfterChecked(astart, 0) + "', " +
                                " aend = '" + GlobalMemberValues.getDBTextAfterChecked(aend, 0) + "', " +
                                " estart = '" + GlobalMemberValues.getDBTextAfterChecked(estart, 0) + "', " +
                                " eend = '" + GlobalMemberValues.getDBTextAfterChecked(eend, 0) + "', " +
                                " nstart = '" + GlobalMemberValues.getDBTextAfterChecked(nstart, 0) + "', " +
                                " nend = '" + GlobalMemberValues.getDBTextAfterChecked(nend, 0) + "', " +

                                " takeawayyn = '" + GlobalMemberValues.getDBTextAfterChecked(takeawayyn, 0) + "', " +
                                " deliveryyn = '" + GlobalMemberValues.getDBTextAfterChecked(deliveryyn, 0) + "', " +
                                " deliverycharge = '" + GlobalMemberValues.getDBTextAfterChecked(deliverycharge, 0) + "', " +
                                " deliveryfree = '" + GlobalMemberValues.getDBTextAfterChecked(deliveryfree, 0) + "', " +
                                " pickupcharge = '" + GlobalMemberValues.getDBTextAfterChecked(pickupcharge, 0) + "', " +

                                " picktype_here = '" + GlobalMemberValues.getDBTextAfterChecked(picktype_here, 0) + "', " +
                                " picktype_togo = '" + GlobalMemberValues.getDBTextAfterChecked(picktype_togo, 0) + "', " +
                                " picktype_delivery = '" + GlobalMemberValues.getDBTextAfterChecked(picktype_delivery, 0) + "', " +

                                " phoneorder = '" + GlobalMemberValues.getDBTextAfterChecked(phoneorder, 0) + "', " +

                                " kitchenprintsu = '" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintsu, 0) + "', " +

                                " minpayforsign = '" + GlobalMemberValues.getDBTextAfterChecked(minpayforsign, 0) + "', " +

                                " storetype = '" + GlobalMemberValues.getDBTextAfterChecked(storetype, 0) + "', " +
                                " franchiseyn = '" + GlobalMemberValues.getDBTextAfterChecked(franchiseyn, 0) + "', " +

                                " ordercompletesmsyn = '" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsyn, 0) + "', " +
                                " ordercompletesmsremain = '" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsremain, 0) + "', " +
                                " ordercompletesmsterm = '" + GlobalMemberValues.getDBTextAfterChecked(ordercompletesmsterm, 0) + "', " +

                                " togotaxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(togotaxfreeyn, 0) + "', " +
                                " togotaxfreetype = '" + GlobalMemberValues.getDBTextAfterChecked(togotaxfreetype, 0) + "', " +
                                " heretaxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(heretaxfreeyn, 0) + "', " +
                                " heretaxfreetype = '" + GlobalMemberValues.getDBTextAfterChecked(heretaxfreetype, 0) + "', " +
                                " deliverytaxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(deliverytaxfreeyn, 0) + "', " +
                                " deliverytaxfreetype = '" + GlobalMemberValues.getDBTextAfterChecked(deliverytaxfreetype, 0) + "', " +
                                " dineintaxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(dineintaxfreeyn, 0) + "', " +
                                " dineintaxfreetype = '" + GlobalMemberValues.getDBTextAfterChecked(dineintaxfreetype, 0) + "', " +
                                " mobiletaxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(mobiletaxfreeyn, 0) + "', " +
                                " mobiletaxfreetype = '" + GlobalMemberValues.getDBTextAfterChecked(mobiletaxfreetype, 0) + "', " +
                                " taxexemptbytotalyn = '" + GlobalMemberValues.getDBTextAfterChecked(taxexemptbytotalyn, 0) + "', " +
                                " modifierviewtype = '" + GlobalMemberValues.getDBTextAfterChecked(modifierviewtype, 0) + "', " +

                                " posdualdpimg = '" + GlobalMemberValues.getDBTextAfterChecked(posdualdpimg, 0) + "', " +
                                " posdualdptype = '" + GlobalMemberValues.getDBTextAfterChecked(posdualdptype, 0) + "', " +

                                " posreceiptlogimg = '" + GlobalMemberValues.getDBTextAfterChecked(posreceiptlogimg, 0) + "', " +

                                " scaleuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(scaleuseyn, 0) + "', " +
                                " scaleunit = '" + GlobalMemberValues.getDBTextAfterChecked(scaleunit, 0) + "', " +

                                " scaleminweight = '" + GlobalMemberValues.getDBTextAfterChecked(scaleminweight, 0) + "', " +
                                " scalemaxweight = '" + GlobalMemberValues.getDBTextAfterChecked(scalemaxweight, 0) + "', " +

                                " postype = '" + GlobalMemberValues.getDBTextAfterChecked(postype, 0) + "', " +

                                " scaledecimaltwiceyn = '" + GlobalMemberValues.getDBTextAfterChecked(scaledecimaltwiceyn, 0) + "', " +

                                " webpayuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(webpayuseyn, 0) + "', " +
                                " curbsidepickupyn = '" + GlobalMemberValues.getDBTextAfterChecked(curbsidepickupyn, 0) + "', " +
                                " sideorderyn = '" + GlobalMemberValues.getDBTextAfterChecked(sideorderyn, 0) + "', " +

                                " gratuityuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(gratuityuseyn, 0) + "', " +
                                " gratuitytype = '" + GlobalMemberValues.getDBTextAfterChecked(gratuitytype, 0) + "', " +
                                " gratuityvalue = '" + GlobalMemberValues.getDBTextAfterChecked(gratuityvalue, 0) + "', " +
                                " gratuitycustomercount = '" + GlobalMemberValues.getDBTextAfterChecked(gratuitycustomercount, 0) + "', " +

                                " cashoutreportitems = '" + GlobalMemberValues.getDBTextAfterChecked(cashoutreportitems, 0) + "', " +

                                " divideruseyn = '" + GlobalMemberValues.getDBTextAfterChecked(divideruseyn, 0) + "', " +

                                " labelprinteruse = '" + GlobalMemberValues.getDBTextAfterChecked(labelprinteruse, 0) + "', " +

                                " managerpwdnz = '" + GlobalMemberValues.getDBTextAfterChecked(managerpwdnz, 0) + "', " +

                                " suggestiontiptype = '" + GlobalMemberValues.getDBTextAfterChecked(suggestiontiptype, 0) + "', " +

                                " commongratuitytype = '" + GlobalMemberValues.getDBTextAfterChecked(commongratuitytype, 0) + "', " +

                                " multistationsyncuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(multistationsyncuseyn, 0) + "', " +

                                " addpaytype = '" + GlobalMemberValues.getDBTextAfterChecked(addpaytype, 0) + "', " +
                                " addpaymoneytype = '" + GlobalMemberValues.getDBTextAfterChecked(addpaymoneytype, 0) + "', " +
                                " addpaymoney = '" + GlobalMemberValues.getDBTextAfterChecked(addpaymoney, 0) + "', " +
                                " addpaytaxtype = '" + GlobalMemberValues.getDBTextAfterChecked(addpaytaxtype, 0) + "', " +
                                " addpayname = '" + GlobalMemberValues.getDBTextAfterChecked(addpayname, 0) + "', " +

                                " scaleautoweighyn = '" + GlobalMemberValues.getDBTextAfterChecked(scaleautoweighyn, 0) + "', " +

                                " wmuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(wmuseyn, 0) + "', " +
                                " wmapiip = '" + GlobalMemberValues.getDBTextAfterChecked(wmapiip, 0) + "', " +

                                // 05112023
                                " wmoptionstsr = '" + GlobalMemberValues.getDBTextAfterChecked(wmoptionstsr, 0) + "', " +
                                " addpaycustomerselectyn = '" + GlobalMemberValues.getDBTextAfterChecked(addpaycustomerselectyn, 0) + "', " +

                                // 06212023
                                " addpayitempriceshowyn = '" + GlobalMemberValues.getDBTextAfterChecked(addpayitempriceshowyn, 0) + "', " +
                                " cashinoutstartendcashyn = '" + GlobalMemberValues.getDBTextAfterChecked(cashinoutstartendcashyn, 0) + "', " +

                                // 08012023
                                " gratuitydelyn = '" + GlobalMemberValues.getDBTextAfterChecked(gratuitydelyn, 0) + "', " +
                                " crmuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(crmuseyn, 0) + "', " +
                                " cashdcshowonreceiptyn = '" + GlobalMemberValues.getDBTextAfterChecked(cashdcshowonreceiptyn, 0) + "', " +
                                " cashdctaxshowyn = '" + GlobalMemberValues.getDBTextAfterChecked(cashdctaxshowyn, 0) + "', " +

                                // 03252024
                                " torderuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(torderuseyn, 0) + "', " +
                                " torderkey = '" + GlobalMemberValues.getDBTextAfterChecked(torderkey, 0) + "', " +

                                // 04302024
                                " qsronrestaurantyn = '" + GlobalMemberValues.getDBTextAfterChecked(qsronrestaurantyn, 0) + "', " +

                                // 05302024
                                " torderapikey = '" + GlobalMemberValues.getDBTextAfterChecked(torderapikey, 0) + "', " +
                                " torderpartnerid = '" + GlobalMemberValues.getDBTextAfterChecked(torderpartnerid, 0) + "', " +
                                " torderapiurl = '" + GlobalMemberValues.getDBTextAfterChecked(torderapiurl, 0) + "' " +

                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storegeneral", "mQueryCollection : " + mQueryCollection);

                        // 화면재개 지연시간 지정
                        if (!GlobalMemberValues.isStrEmpty(totaloptionitem)) {
                            GlobalMemberValues.RESTARTSCREEN_DELYTIME = totaloptionitem;
                        }

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        sidx = "";
                        managerpwd = "";
                        tax1 = "";
                        tax2 = "";
                        tax3 = "";
                        tax4 = "";
                        tax5 = "";
                        rspuseyn = "";
                        rsprate = "";
                        categorysu = "";
                        delyn = "";
                        wdate = "";
                        mdate = "";

                        mstart = "";
                        mend = "";
                        astart = "";
                        aend = "";
                        estart = "";
                        eend = "";
                        nstart = "";
                        nend = "";

                        takeawayyn = "";
                        deliveryyn = "";
                        deliverycharge = "";
                        deliveryfree = "";
                        pickupcharge = "";

                        picktype_here = "";
                        picktype_togo = "";
                        picktype_delivery = "";
                        phoneorder = "";
                        kitchenprintsu = "";

                        minpayforsign = "";

                        storetype = "";
                        franchiseyn = "";

                        ordercompletesmsyn = "";
                        ordercompletesmsremain = "";
                        ordercompletesmsterm = "";

                        togotaxfreeyn = "";
                        togotaxfreetype = "";
                        heretaxfreeyn = "";
                        heretaxfreetype = "";
                        deliverytaxfreeyn = "";
                        deliverytaxfreetype = "";
                        dineintaxfreeyn = "";
                        dineintaxfreetype = "";
                        mobiletaxfreeyn = "";
                        mobiletaxfreetype = "";
                        taxexemptbytotalyn = "";
                        modifierviewtype = "";

                        posdualdpimg = "";
                        posdualdptype = "";

                        posreceiptlogimg = "";

                        scaleuseyn = "";
                        scaleunit = "";

                        scaleminweight = "";
                        scalemaxweight = "";

                        postype = "";

                        scaledecimaltwiceyn = "";

                        webpayuseyn = "";
                        curbsidepickupyn = "";
                        sideorderyn = "";

                        gratuityuseyn = "";
                        gratuitytype = "";
                        gratuityvalue = "";
                        gratuitycustomercount = "";

                        cashoutreportitems = "";

                        divideruseyn = "";

                        labelprinteruse = "";

                        managerpwdnz = "";

                        suggestiontiptype = "";

                        commongratuitytype = "";

                        multistationsyncuseyn = "";

                        addpaytype = "";
                        addpaymoneytype = "";
                        addpaymoney = "";
                        addpaytaxtype = "";
                        addpayname = "";

                        scaleautoweighyn = "";

                        wmuseyn = "";
                        wmapiip = "";

                        wmoptionstsr = "";
                        addpaycustomerselectyn = "";

                        addpayitempriceshowyn = "";
                        cashinoutstartendcashyn = "";

                        gratuitydelyn = "";
                        crmuseyn = "";
                        cashdcshowonreceiptyn = "";
                        cashdctaxshowyn = "";

                        // 03252024
                        torderuseyn = "";
                        torderkey = "";

                        // 04302024
                        qsronrestaurantyn = "";

                        // 05302024
                        torderapikey = "";
                        torderpartnerid = "";
                        torderapiurl = "";

                        totaloptionitem = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storegeneral", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_storegeneral", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_storegeneral", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_storegeneral", "----------------------------\n");
        }
        return null;
    }
}
