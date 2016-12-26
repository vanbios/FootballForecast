package com.vanbios.footballforecast.common.repository.remote;


import com.vanbios.footballforecast.common.api.RestApi;
import com.vanbios.footballforecast.common.api.model.BaseResponseArray;
import com.vanbios.footballforecast.common.api.model.BaseResponseObject;
import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;
import com.vanbios.footballforecast.news.models.News;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

class RemoteRepositoryImpl implements RemoteRepository {

    private RestApi restApi;

    RemoteRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<BaseResponseArray<Forecast>> getForecastListPaging(int offset, int limit) {
        return restApi.getForecastList(offset, limit);
    }

    @Override
    public Observable<BaseResponseArray<News>> getNewsListPaging(int offset, int limit) {
        return restApi.getNewsList(offset, limit);
    }

    @Override
    public Observable<BaseResponseObject<Infograf>> getInfografById(int id) {
        return restApi.getInfograf(id);
    }
}