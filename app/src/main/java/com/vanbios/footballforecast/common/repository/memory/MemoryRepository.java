package com.vanbios.footballforecast.common.repository.memory;

import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;
import com.vanbios.footballforecast.news.models.News;

import java.util.List;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface MemoryRepository {

    void setNewsList(List<News> list);

    void setForecastList(List<Forecast> list);

    void addItemsToForecastList(List<Forecast> list);

    int getForecastListSize();

    int getLastForecastId();

    void addItemsToNewsList(List<News> list);

    int getNewsListSize();

    int getLastNewsId();

    void putNewInfograf(int id, Infograf infograf);

    Infograf getInfografById(int id);

    Observable<Infograf> getInfografObservableById(int id);

    List<Forecast> getForecastList();

    List<News> getNewsList();

    Forecast getForecastById(int id);

    News getNewsById(int id);

    Observable<List<News>> getNewsPagingObservable(int offset, int limit);

    Observable<List<Forecast>> getForecastPagingObservable(int offset, int limit);
}
