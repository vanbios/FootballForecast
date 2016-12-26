package com.vanbios.footballforecast.common.api;

import com.vanbios.footballforecast.common.api.model.BaseResponseArray;
import com.vanbios.footballforecast.common.api.model.BaseResponseObject;
import com.vanbios.footballforecast.news.models.News;
import com.vanbios.footballforecast.forecast.models.Infograf;
import com.vanbios.footballforecast.forecast.models.Forecast;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface RestApi {

    @GET("apiprognoz/list")
    Observable<BaseResponseArray<Forecast>> getForecastList(@Query("id") int id, @Query("limit") int limit);

    @GET("apinews/list")
    Observable<BaseResponseArray<News>> getNewsList(@Query("id") int id, @Query("limit") int limit);

    @GET("apiprognoz/infograf/{id}")
    Observable<BaseResponseObject<Infograf>> getInfograf(@Path("id") int id);
}