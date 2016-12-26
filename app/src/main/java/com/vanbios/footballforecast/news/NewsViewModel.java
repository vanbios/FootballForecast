package com.vanbios.footballforecast.news;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Builder
@Getter
class NewsViewModel {

    private int id;
    private String title;
    private String imageUrl;
    private String time;
    private String date;
}