package com.vanbios.footballforecast.common.repository.remote;

import com.vanbios.footballforecast.common.api.model.BaseResponseArray;
import com.vanbios.footballforecast.common.api.model.BaseResponseObject;
import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;
import com.vanbios.footballforecast.news.models.News;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface RemoteRepository {

    Observable<BaseResponseArray<Forecast>> getForecastListPaging(int offset, int limit);

    Observable<BaseResponseArray<News>> getNewsListPaging(int offset, int limit);

    Observable<BaseResponseObject<Infograf>> getInfografById(int id);
}