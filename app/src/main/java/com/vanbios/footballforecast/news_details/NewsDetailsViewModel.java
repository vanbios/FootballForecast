package com.vanbios.footballforecast.news_details;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Builder
@Getter
class NewsDetailsViewModel {

    private String imageUrl;
    private String time;
    private String date;
    private String html;
    private String url;
}