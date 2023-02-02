package ir.netrira.core.application.utils;

import com.ghasemkiani.util.SimplePersianCalendar;

import java.util.*;

public class DateUtil {

    private static String[] persianMoths = new String[]{"null","فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی","بهمن", "اسفند"};
    private static String[] daysOfWeek = new String[]{"شنبه", "یک\u200Cشنبه", "دوشنبه", "سه\u200Cشنبه", "چهارشنبه", "پنج\u200Cشنبه", "جمعه"};

    public static String getCurrentDate() {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        int day = persianCalendar.getDateFields().getDay();
        int month = persianCalendar.getDateFields().getMonth() + 1;
        int year = persianCalendar.getDateFields().getYear();
        persianCalendar.setTime(clonedCal.getTime());
        return getDate(year, month, day);
    }

    public static String getCurrentDate(Integer fromNow) {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.add(Calendar.DATE, fromNow);
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        int day = persianCalendar.getDateFields().getDay();
        int month = persianCalendar.getDateFields().getMonth() + 1;
        int year = persianCalendar.getDateFields().getYear();
        persianCalendar.setTime(clonedCal.getTime());
        return getDate(year, month, day);
    }

    public static String getCurrentDateTime() {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        int day = persianCalendar.getDateFields().getDay();
        int month = persianCalendar.getDateFields().getMonth() + 1;
        int year = persianCalendar.getDateFields().getYear();
        persianCalendar.setTime(clonedCal.getTime());
        String persianDate = getDate(year, month, day);

        int hour = persianCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = persianCalendar.get(Calendar.MINUTE);
        int second = persianCalendar.get(Calendar.SECOND);
        int millSecond = persianCalendar.get(Calendar.MILLISECOND);
        String persianTime = getTime(hour, minute, second, millSecond);
        return persianDate + "-" + persianTime;
    }

    public static String getCurrentTime() {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        persianCalendar.setTime(clonedCal.getTime());

        int hour = persianCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = persianCalendar.get(Calendar.MINUTE);
        int second = persianCalendar.get(Calendar.SECOND);
        int millSecond = persianCalendar.get(Calendar.MILLISECOND);
        String persianTime = getTime(hour, minute, second, millSecond);
        return persianTime;
    }

    public static String getDate(int year, int month, int day) {
        return String.format("%04d/%02d/%02d", year, month, day);
    }

    private static String getTime(int hour, int minute, int second, int millSecond) {
        return String.format("%02d:%02d.%02d.%03d", hour, minute, second, millSecond);
    }

    public static Boolean isAfter(String date, String afterDate) {
        return compareDate(date, afterDate) > 0;
    }

    public static Boolean isBefore(String date, String beforeDate) {
        return compareDate(date, beforeDate) < 0;
    }

    public static Boolean isSame(String date, String otherDate) {
        return compareDate(date, otherDate) == 0;
    }

    public static Boolean isAfterOrSame(String date, String afterOrSameDate) {
        return compareDate(date, afterOrSameDate) >= 0;
    }

    private static int compareDate(String date, String otherDate) {
        if (Objects.nonNull(date) && Objects.nonNull(otherDate) && date.length() == otherDate.length()) {
            return date.compareTo(otherDate);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static String getFormattedDate(String date, int dayOfWeek) {
        String[] splittedDate = date.split("/");
        return daysOfWeek[dayOfWeek] + "  " + Integer.valueOf(splittedDate[2]) + "  " + persianMoths[Integer.valueOf(splittedDate[1])];
    }

    public static String getFormattedDate(Integer year, Integer month) {
        return persianMoths[month] + "  " + year;
    }

    public static String getFormattedDateWithoutName(String date) {
        String[] splittedDate = date.split("/");
        return Integer.valueOf(splittedDate[2]) + "  " + persianMoths[Integer.valueOf(splittedDate[1])];
    }

    public static String getPersianMonth(String date) {
        String[] splittedDate = date.split("/");
        return persianMoths[Integer.valueOf(splittedDate[1])];
    }

    public static String getPersianMonth(Integer month) {
        return persianMoths[month] + " ماه";
    }

    public static String getFormattedWeek(List<ir.netrira.core.models.management.Calendar> days) {
        String[] splittedFirstDate = days.get(0).getDate().split("/");
        String[] splittedLastDate = days.get(6).getDate().split("/");
        Integer firstDayMounth = Integer.valueOf(splittedFirstDate[1]);
        Integer lastDayMounth = Integer.valueOf(splittedLastDate[1]);
        Integer firstDay = Integer.valueOf(splittedFirstDate[2]);
        Integer lastDay = Integer.valueOf(splittedLastDate[2]);
        if (lastDayMounth == firstDayMounth) {
            return firstDay + " تا " + lastDay + " " + persianMoths[firstDayMounth];
        }
        return firstDay + " " + persianMoths[firstDayMounth] + " تا " + lastDay + " " + persianMoths[lastDayMounth];

    }

    public static String getPersianDaysOfWeek(int dayNum) throws Exception {
        if (dayNum <= 7) {
            return daysOfWeek[dayNum];
        }
        throw new Exception("invalid Number Of day");  // TODO: 7/15/2022 change exception type
    }

    public static String getFormatedTime(Integer hour,Integer minute){
        return String.format("%02d:%02d", hour,minute);
    }

//1400/12/14-11:24.56.005
}
