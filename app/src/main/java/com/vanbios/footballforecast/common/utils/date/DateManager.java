package com.vanbios.footballforecast.common.utils.date;

/**
 * @author Ihor Bilous
 */

public interface DateManager {

    int DAY = 86_400_000;

    String longFullDateToString(long millis);

    String longShortDateToString(long millis);

    String longTimeToString(long millis);

    String longFullDateAndTimeToString(long millis);

    long getTodayStartTime();

    long getTodayEndTime();

    long getTomorrowStartTime();

    long getTomorrowEndTime();

    long dateToMillis(Integer date);

    long getCurrentSeconds();
}
