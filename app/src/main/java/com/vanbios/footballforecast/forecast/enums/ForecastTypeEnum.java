package com.vanbios.footballforecast.forecast.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ForecastTypeEnum {

    SOCCER("Футбол", 1),
    HOCKEY("Хоккей", 2),
    BASKETBALL("Баскет", 3),
    TENNIS("Теннис", 4);


    private String title;
    private int type;
}
