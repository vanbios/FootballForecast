package com.vanbios.footballforecast.common.utils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ihor Bilous
 */

class DateManagerImpl implements DateManager {

    @Override
    public String longFullDateToString(long millis) {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date(millis));
    }

    @Override
    public String longShortDateToString(long millis) {
        return new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(new Date(millis));
    }

    @Override
    public String longTimeToString(long millis) {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(millis));
    }

    @Override
    public String longFullDateAndTimeToString(long millis) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date(millis));
    }

    @Override
    public long getTodayStartTime() {
        return getStartOfDay(currentTime());
    }

    @Override
    public long getTodayEndTime() {
        return getEndOfDay(currentTime());
    }

    @Override
    public long getTomorrowStartTime() {
        return getStartOfDay(currentTime() + DAY);
    }

    @Override
    public long getTomorrowEndTime() {
        return getEndOfDay(currentTime() + DAY);
    }

    @Override
    public long dateToMillis(Integer date) {
        return Long.valueOf(date) * 1000;
    }

    @Override
    public long getCurrentSeconds() {
        return currentTime() / 1000;
    }

    private long getEndOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    private long getStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}