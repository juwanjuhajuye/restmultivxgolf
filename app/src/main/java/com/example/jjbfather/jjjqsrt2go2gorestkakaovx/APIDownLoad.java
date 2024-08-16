package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-02.
 */
public class APIDownLoad {

    public Vector<String> getApiDownLoadVectorData (String tblName) {

        Vector<String> mVector = new Vector<String>();

        switch (tblName) {
            /** salon_storeservice_main 다운로드 *************************************************/
            case "salon_storeservice_main" : {
                APIDownLoad_salon_storeservice_main apidownloadInstance = new APIDownLoad_salon_storeservice_main();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storeservice_sub 다운로드 *************************************************/
            case "salon_storeservice_sub" : {
                APIDownLoad_salon_storeservice_sub apidownloadInstance = new APIDownLoad_salon_storeservice_sub();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_sub_setmenu 다운로드 *************************************************/
            case "salon_storeservice_sub_setmenu" : {
//                APIDownLoad_salon_storeservice_sub_setmenu apidownloadInstance = new APIDownLoad_salon_storeservice_sub_setmenu();
//                apidownloadInstance.execute(null, null, null);
//                try {
//                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                    if (apidownloadInstance.mFlag) {
//                        mVector = apidownloadInstance.sqlQueryVec;
//                    }
//                } catch (InterruptedException e) {
//                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
//                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeinfo 다운로드 *************************************************/
            case "salon_storeinfo" : {
                APIDownLoad_salon_storeinfo apidownloadInstance = new APIDownLoad_salon_storeinfo();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeinfo_worktime 다운로드 *************************************************/
            case "salon_storeinfo_worktime" : {
                APIDownLoad_salon_storeinfo_worktime apidownloadInstance = new APIDownLoad_salon_storeinfo_worktime();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeinfo_worktime_forPOS 다운로드 *************************************************/
            case "salon_storeinfo_worktime_forPOS" : {
                APIDownLoad_salon_storeinfo_worktime_forPOS apidownloadInstance = new APIDownLoad_salon_storeinfo_worktime_forPOS();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storestationinfo 다운로드 *************************************************/
            case "salon_storestationinfo" : {
                APIDownLoad_salon_storestationinfo apidownloadInstance = new APIDownLoad_salon_storestationinfo();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storegeneral 다운로드 *************************************************/
            case "salon_storegeneral" : {
                APIDownLoad_salon_storegeneral apidownloadInstance = new APIDownLoad_salon_storegeneral();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeproduct 다운로드 *************************************************/
            case "salon_storeproduct" : {
                APIDownLoad_salon_storeproduct apidownloadInstance = new APIDownLoad_salon_storeproduct();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_member 다운로드 *************************************************/
            case "salon_member" : {
                if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                    APIDownLoad_salon_member apidownloadInstance = new APIDownLoad_salon_member();
                    apidownloadInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apidownloadInstance.mFlag) {
                            mVector = apidownloadInstance.sqlQueryVec;
                        }
                    } catch (InterruptedException e) {
                        GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                }
                break;
            }
            /**************************************************************************************/

            /** member2 다운로드 *************************************************/
            case "member2" : {
                if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                    APIDownLoad_member2 apidownloadInstance = new APIDownLoad_member2();
                    apidownloadInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apidownloadInstance.mFlag) {
                            mVector = apidownloadInstance.sqlQueryVec;
                        }
                    } catch (InterruptedException e) {
                        GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                }
                break;
            }
            /**************************************************************************************/

            /** member1 다운로드 *************************************************/
            case "member1" : {
                if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                    APIDownLoad_member1 apidownloadInstance = new APIDownLoad_member1();
                    apidownloadInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apidownloadInstance.mFlag) {
                            mVector = apidownloadInstance.sqlQueryVec;
                        }
                    } catch (InterruptedException e) {
                        GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeemployee 다운로드 *************************************************/
            case "salon_storeemployee" : {
                APIDownLoad_salon_storeemployee apidownloadInstance = new APIDownLoad_salon_storeemployee();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storepart 다운로드 *************************************************/
            case "salon_storepart" : {
                APIDownLoad_salon_storepart apidownloadInstance = new APIDownLoad_salon_storepart();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storepart_employee 다운로드 *************************************************/
            case "salon_storepart_employee" : {
                APIDownLoad_salon_storepart_employee apidownloadInstance = new APIDownLoad_salon_storepart_employee();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeproduct_ipkohistory 다운로드 *************************************************/
            case "salon_storeproduct_ipkohistory" : {
                APIDownLoad_salon_storeproduct_ipkohistory apidownloadInstance = new APIDownLoad_salon_storeproduct_ipkohistory();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeemployee_worktime 다운로드 *************************************************/
            case "salon_storeemployee_worktime" : {
                APIDownLoad_salon_storeemployee_worktime apidownloadInstance = new APIDownLoad_salon_storeemployee_worktime();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeemployee_workweekday 다운로드 *************************************************/
            case "salon_storeemployee_workweekday" : {
                APIDownLoad_salon_storeemployee_workweekday apidownloadInstance = new APIDownLoad_salon_storeemployee_workweekday();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storePG 다운로드 *************************************************/
            case "salon_storePG" : {
//                APIDownLoad_salon_storePG apidownloadInstance = new APIDownLoad_salon_storePG();
//                apidownloadInstance.execute(null, null, null);
//                try {
//                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                    if (apidownloadInstance.mFlag) {
//                        mVector = apidownloadInstance.sqlQueryVec;
//                    }
//                } catch (InterruptedException e) {
//                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
//                }
                break;
            }
            /**************************************************************************************/

            /** salon_storegiftcard 다운로드 *************************************************/
            case "salon_storegiftcard" : {
                APIDownLoad_salon_storegiftcard apidownloadInstance = new APIDownLoad_salon_storegiftcard();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** coupon_imgtype 다운로드 *************************************************/
            case "coupon_imgtype" : {
//                APIDownLoad_coupon_imgtype apidownloadInstance = new APIDownLoad_coupon_imgtype();
//                apidownloadInstance.execute(null, null, null);
//                try {
//                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                    if (apidownloadInstance.mFlag) {
//                        mVector = apidownloadInstance.sqlQueryVec;
//                    }
//                } catch (InterruptedException e) {
//                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
//                }
                break;
            }
            /**************************************************************************************/

            /** coupon_issue_history 다운로드 *************************************************/
            case "coupon_issue_history" : {
                if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                    APIDownLoad_coupon_issue_history apidownloadInstance = new APIDownLoad_coupon_issue_history();
                    apidownloadInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apidownloadInstance.mFlag) {
                            mVector = apidownloadInstance.sqlQueryVec;
                        }
                    } catch (InterruptedException e) {
                        GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                }
                break;
            }
            /**************************************************************************************/

            /** coupon_issue_history 다운로드 *************************************************/
            case "coupon" : {
                if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                    APIDownLoad_coupon apidownloadInstance = new APIDownLoad_coupon();
                    apidownloadInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apidownloadInstance.mFlag) {
                            mVector = apidownloadInstance.sqlQueryVec;
                        }
                    } catch (InterruptedException e) {
                        GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storememosel 다운로드 *************************************************/
            case "salon_storememosel" : {
                APIDownLoad_salon_storememosel apidownloadInstance = new APIDownLoad_salon_storememosel();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                        GlobalMemberValues.logWrite("itemdownload2", "APDOWNLOAD 에서.. : " + mVector.toString() + "\n");
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storeservice_option_btn 다운로드 *************************************************/
            case "salon_storeservice_option_btn" : {
                APIDownLoad_salon_storeservice_option_btn apidownloadInstance = new APIDownLoad_salon_storeservice_option_btn();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_option 다운로드 *************************************************/
            case "salon_storeservice_option" : {
                APIDownLoad_salon_storeservice_option apidownloadInstance = new APIDownLoad_salon_storeservice_option();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_option_item 다운로드 *************************************************/
            case "salon_storeservice_option_item" : {
                APIDownLoad_salon_storeservice_option_item apidownloadInstance = new APIDownLoad_salon_storeservice_option_item();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_commonmodifier 다운로드 *************************************************/
            case "salon_storeservice_commonmodifier" : {
                APIDownLoad_salon_storeservice_commonmodifier apidownloadInstance = new APIDownLoad_salon_storeservice_commonmodifier();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_commonmodifier_item 다운로드 *************************************************/
            case "salon_storeservice_commonmodifier_item" : {
                APIDownLoad_salon_storeservice_commonmodifier_item apidownloadInstance = new APIDownLoad_salon_storeservice_commonmodifier_item();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_additional 다운로드 *************************************************/
            case "salon_storeservice_additional" : {
                APIDownLoad_salon_storeservice_additional apidownloadInstance = new APIDownLoad_salon_storeservice_additional();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_store_cashinoutreason 다운로드 *************************************************/
            case "salon_store_cashinoutreason" : {
                APIDownLoad_salon_store_cashinoutreason apidownloadInstance = new APIDownLoad_salon_store_cashinoutreason();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                        GlobalMemberValues.logWrite("itemdownload2", "APDOWNLOAD 에서.. : " + mVector.toString() + "\n");
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_store_deliveryappcompany 다운로드 *************************************************/
            case "salon_store_deliveryappcompany" : {
                APIDownLoad_salon_store_deliveryappcompany apidownloadInstance = new APIDownLoad_salon_store_deliveryappcompany();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                        GlobalMemberValues.logWrite("itemdownload2", "APDOWNLOAD 에서.. : " + mVector.toString() + "\n");
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storeservice_sub_timemenuprice 다운로드 ***************************************/
            case "salon_storeservice_sub_timemenuprice" : {
                APIDownLoad_salon_storeservice_sub_timemenuprice apidownloadInstance = new APIDownLoad_salon_storeservice_sub_timemenuprice();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_storediscountbutton 다운로드 ***************************************/
            case "salon_storediscountbutton" : {
                APIDownLoad_salon_storediscountbutton apidownloadInstance = new APIDownLoad_salon_storediscountbutton();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_store_tare 다운로드 ***************************************/
            case "salon_store_tare" : {
                APIDownLoad_salon_store_tare apidownloadInstance = new APIDownLoad_salon_store_tare();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_store_restaurant_table 다운로드 ***************************************/
            case "salon_store_restaurant_table" : {
                APIDownLoad_salon_store_restaurant_table apidownloadInstance = new APIDownLoad_salon_store_restaurant_table();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

            /** salon_store_restaurant_tablezone 다운로드 ***************************************/
            case "salon_store_restaurant_tablezone" : {
                APIDownLoad_salon_store_restaurant_tablezone apidownloadInstance = new APIDownLoad_salon_store_restaurant_tablezone();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storespecialrequest 다운로드 ***************************************/
            case "salon_storespecialrequest" : {
                APIDownLoad_salon_storespecialrequest apidownloadInstance = new APIDownLoad_salon_storespecialrequest();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            /** salon_storesmstextsample 다운로드 ***************************************/
            case "salon_storesmstextsample" : {
                APIDownLoad_salon_storesmstextsample apidownloadInstance = new APIDownLoad_salon_storesmstextsample();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            // 08092023
            /** salon_storeemployeerole 다운로드 ***************************************/
            case "salon_storeemployeerole" : {
                APIDownLoad_salon_storeemployeerole apidownloadInstance = new APIDownLoad_salon_storeemployeerole();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            // 10272023
            /** salon_storeitemdeletereason 다운로드 ***************************************/
            case "salon_storeitemdeletereason" : {
                APIDownLoad_salon_storeitemdeletereason apidownloadInstance = new APIDownLoad_salon_storeitemdeletereason();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/



            // 02192024
            /** salon_storememberlevel 다운로드 ***************************************/
            case "salon_storememberlevel" : {
                APIDownLoad_salon_storememberlevel apidownloadInstance = new APIDownLoad_salon_storememberlevel();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


            // 08162024
            /** salon_storebreaktime 다운로드 ***************************************/
            case "salon_storebreaktime" : {
                APIDownLoad_salon_storebreaktime apidownloadInstance = new APIDownLoad_salon_storebreaktime();
                apidownloadInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apidownloadInstance.mFlag) {
                        mVector = apidownloadInstance.sqlQueryVec;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/


        }
        return mVector;
    }
}
