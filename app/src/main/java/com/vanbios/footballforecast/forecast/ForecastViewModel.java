package com.vanbios.footballforecast.forecast;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Builder
@Getter
class ForecastViewModel {

    private int id;
    private String title;
    private String subTitle;
    private String imageUrl;
    private boolean isHot;
    private boolean hasInfograf;
    private String textKoef;
}
