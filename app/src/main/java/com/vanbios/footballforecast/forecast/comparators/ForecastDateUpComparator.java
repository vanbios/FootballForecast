package com.vanbios.footballforecast.forecast.comparators;


import com.vanbios.footballforecast.forecast.models.Forecast;

import java.util.Comparator;

/**
 * @author Ihor Bilous
 */

public class ForecastDateUpComparator implements Comparator<Forecast> {

    @Override
    public int compare(Forecast forecast1, Forecast forecast2) {
        return forecast1.getDate().compareTo(forecast2.getDate());
    }
}