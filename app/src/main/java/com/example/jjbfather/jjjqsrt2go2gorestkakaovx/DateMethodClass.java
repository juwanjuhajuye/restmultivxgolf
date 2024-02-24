package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-15.
 */
public class DateMethodClass {
    /**
     * 두날짜 사이의 일수를 리턴
     * @param fromDate mm-dd-yyyy 형식의 시작일
     * @param toDate mm-dd-yyyy 형식의 종료일
     * @return 두날짜 사이의 일수
     */
    public static int getDiffDayCount(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE);

        try {
            return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate)
                    .getTime()) / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴
     * ( 시작일과 종료일을 모두 포함한다 )
     * @param fromDate mm-dd-yyyy 형식의 시작일
     * @param toDate mm-dd-yyyy 형식의 종료일
     * @return mm-dd-yyyy 형식의 날짜가 담긴 배열
     */
    public static String[] getDiffDays(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE);

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(sdf.parse(fromDate));
        } catch (Exception e) {
        }

        int count = getDiffDayCount(fromDate, toDate);

        // 시작일부터
        cal.add(Calendar.DATE, -1);

        // 데이터 저장
        List list = new ArrayList();

        for (int i = 0; i <= count; i++) {
            cal.add(Calendar.DATE, 1);

            list.add(sdf.format(cal.getTime()));
        }

        String[] result = new String[list.size()];

        list.toArray(result);

        return result;
    }

    /**
     * 시작일부터 종료일까지 사이의 월을 배열에 담아 리턴
     * ( 시작일과 종료일을 모두 포함한다 )
     * @param fromDate mm-dd-yyyy 형식의 시작일
     * @param toDate mm-dd-yyyy 형식의 종료일
     * @return mm-dd-yyyy 형식의 날짜가 담긴 배열
     */
    public static String[] getDiffMonth(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(sdf.parse(fromDate));
        } catch (Exception e) {
        }

        int count = getDiffDayCount(fromDate, toDate);

        // 시작일부터
        cal.add(Calendar.MONTH, -1);

        // 데이터 저장
        List list = new ArrayList();

        for (int i = 0; i <= count; i++) {
            cal.add(Calendar.MONTH, 1);

            list.add(sdf.format(cal.getTime()));
        }

        String[] result = new String[list.size()];

        list.toArray(result);

        return result;
    }


    public static long getDiffDaysOfDate(String fromDate, String toDate){
        String start = fromDate;
        String end = toDate;

        long returnValue = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            //System.out.println("날짜차이=" + diffDays);

            returnValue = diffDays;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    // 현재 날짜에서 변경 요청한  날짜 더하거나 빼서 리턴
    // @param  집어넣기
    // @return mm-dd-yyyy 형식의 날짜가 담긴 날짜

    public static String previosDate(int sub){
        SimpleDateFormat fmt=new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE);
        Calendar c= Calendar.getInstance();
        Date date=null;

        try{
            date=fmt.parse(fmt.format(new Date()));
            c.setTime(date);
            c.add(Calendar.DAY_OF_YEAR, sub);

        }catch(Exception ex){
        }
        return fmt.format(c.getTime());

    }

    // 현재 날짜에서 변경 요청한  월 더하거나 빼서
     // @param 숫자(월수) 집어넣기
     // @return mm-dd-yyyy 형식의 날짜가 담긴 날짜

    public static String previosMonth(int sub){
        SimpleDateFormat fmt=new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE);
        Calendar c= Calendar.getInstance();
        Date date=null;

        try{
            date=fmt.parse(fmt.format(new Date()));
            c.setTime(date);
            c.add(Calendar.MONTH, sub);

        }catch(Exception ex){
        }
        return fmt.format(c.getTime());

    }

    // 요청한 날짜에 요청한 날수만큼 더하거나 빼기
    public static String getCustomEditDate(String paramDate, int editYear, int editMonth, int editDay) {
        String retDate = "";
        SimpleDateFormat df = new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE_KOR);
        try {
            String tempYear = "";
            String tempMonth = "";
            String tempDay = "";
            if (!GlobalMemberValues.isStrEmpty(paramDate)) {
                String[] tempDate = paramDate.split("-");
                tempMonth = tempDate[0];
                if (tempDate.length > 1) {
                    tempDay = tempDate[1];
                }
                if (tempDate.length > 2) {
                    tempYear = tempDate[2];
                }

                paramDate = tempYear + "-" +tempMonth + "-" +tempDay;

                Date date = df.parse(paramDate);
                // 날짜 더하기
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, editDay);
                cal.add(Calendar.MONTH, editMonth);
                cal.add(Calendar.YEAR, editYear);
                retDate = df.format(cal.getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retDate;
    }

     // 날짜에 - 를 넣는다.
     // @param mm-dd-yyyy 형식
     // @return yyyy-MM-dd 형식의 날짜가 담긴 날짜

    public static String addDash(String dash){
        String dash_temp = "";
        if ((dash == null) || (dash.equals(""))) {
            return "";
        } else {
            dash_temp = replace(dash, "-", "");
            return dash_temp.substring(4,6) + "-" + dash_temp.substring(6,8) + "-" + dash_temp.substring(0,4);
        }
    }

    public static String getCustomDate(String tempDate) {
        String retDate = "";
        if (!GlobalMemberValues.isStrEmpty(tempDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd", Locale.KOREA);
            retDate = formatter.format(tempDate);
        }
        return retDate;
    }

    /*입력한 날짜기 유효한 날짜인지 체크*/
    public static boolean checkDate(String year, String month, String day) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

            Date result = formatter.parse(year + "." + month + "." + day);
            String resultStr = formatter.format(result);

            if (resultStr.equalsIgnoreCase(year + "." + month + "." + day))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 입력한 시간이 유효한지 체크
     * @param hh   시간
     * @param mm   분
     * @param ss   초
     * @return     boolean
     */
    public static boolean checkTime(String hh, String mm, String ss) {
        try {
            int h = Integer.parseInt(hh);
            int m = Integer.parseInt(mm);
            int s = Integer.parseInt(ss);

            if (h < 0 || h > 23)
                return false;
            if (m < 0 || m > 59)
                return false;
            if (s < 0 || s > 50)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 현재 시간과 비교하기
     * @param nowDate  현재 시간.
     * @param inputDate    입력 날짜.
     * @return boolean  TRUE / FALSE
     */
    public static boolean getHourDiff(String nowDate, String inputDate) {

        int diff_val = 0;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try{
            Date sendDate1 = sdformat.parse(nowDate);
            Date sendDate2 = sdformat.parse(inputDate);
            java.sql.Timestamp sendStamp1 = new java.sql.Timestamp(sendDate1.getTime());
            java.sql.Timestamp sendStamp2 = new java.sql.Timestamp(sendDate2.getTime());

            diff_val = sendStamp2.compareTo(sendStamp1);
        }catch(Exception ex){
        }
        if (diff_val > 0) return true;
        else return false;
    }

    /***
     * 현재 년도 가져오기
     * @return String
     */
    public static String nowYearGet() {
        Calendar calendar = Calendar.getInstance();
        return Integer.toString(calendar.get ( Calendar.YEAR ));
    }

    /***
     * 현재 월 가져오기
     * @return String
     */
    public static String nowMonthGet() {
        Calendar calendar = Calendar.getInstance();
        int nMonth = calendar.get ( Calendar.MONTH ) + 1;
        if ( nMonth < 10 ) return "0" + Integer.toString(nMonth);
        else return Integer.toString(nMonth);
    }

    /***
     * 현재 일 가져오기
     * @return String
     */
    public static String nowDayGet() {
        Calendar calendar = Calendar.getInstance();
        int nDay = calendar.get ( Calendar.DAY_OF_MONTH );
        if ( nDay < 10 ) return "0" + Integer.toString(nDay);
        else return Integer.toString(nDay);
    }

    /***
     * 현재 시간 가져오기
     * @return String
     */
    public static String nowHourGet() {
        Calendar calendar = Calendar.getInstance();
        int nHour = calendar.get ( Calendar.HOUR_OF_DAY );
        if ( nHour < 10 ) return "0" + Integer.toString(nHour);
        else return Integer.toString(nHour);
    }

    /***
     * 현재 분 가져오기
     * @return String
     */
    public static String nowMinuteGet() {
        Calendar calendar = Calendar.getInstance();
        int nMinute = calendar.get ( Calendar.MINUTE );
        if ( nMinute < 10 ) return "0" + Integer.toString(nMinute);
        else return Integer.toString(nMinute);
    }

    /***
     * 현재 분 가져오기
     * @return String
     */
    public static String nowSecondGet() {
        Calendar calendar = Calendar.getInstance();
        int nSecond = calendar.get ( Calendar.SECOND );
        if ( nSecond < 10 ) return "0" + Integer.toString(nSecond);
        else return Integer.toString(nSecond);
    }

    /**
     * 현재 년월일시분초 값을 가져옴
     * @return mm-dd-yyyyHHmmss 형식의 날짜
     */
    public static String nowDate(){

        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyyHHmmss");
        String ndate = formatter.format(currentTime);
        return ndate;
    }


    /**
     * Method replace 문자열에서 일정 부분을 다른 부분으로 대치하는 메소드
     * @param mainString
     * @param oldString
     * @param newString
     * @return String
     */
    public static String replace(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        if (oldString == null || oldString.length() == 0) {
            return mainString;
        }
        if (newString == null) {
            newString = "";
        }

        int i = mainString.lastIndexOf(oldString);
        if (i < 0)
            return mainString;

        StringBuffer mainSb = new StringBuffer(mainString);

        while (i >= 0) {
            mainSb.replace(i, (i + oldString.length()), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }

}


