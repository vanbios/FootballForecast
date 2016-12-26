package com.vanbios.footballforecast.common.repository.memory;

import com.annimon.stream.Stream;
import com.vanbios.footballforecast.common.api.model.BaseResponseArray;
import com.vanbios.footballforecast.common.api.model.BaseResponseObject;
import com.vanbios.footballforecast.common.repository.remote.RemoteRepository;
import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;
import com.vanbios.footballforecast.news.models.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Ihor Bilous
 */

class MemoryRepositoryImpl implements MemoryRepository {

    private List<Forecast> forecastList;
    private List<News> newsList;
    private Map<Integer, Infograf> infografMap;
    private RemoteRepository remoteRepository;


    MemoryRepositoryImpl(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
        forecastList = new ArrayList<>();
        newsList = new ArrayList<>();
        infografMap = new HashMap<>();
    }

    @Override
    public void setNewsList(List<News> list) {
        newsList.clear();
        addItemsToNewsList(list);
    }

    @Override
    public void setForecastList(List<Forecast> list) {
        forecastList.clear();
        addItemsToForecastList(list);
    }

    @Override
    public void addItemsToForecastList(List<Forecast> list) {
        forecastList.addAll(list);
    }

    @Override
    public int getForecastListSize() {
        return forecastList.size();
    }

    @Override
    public int getLastForecastId() {
        return forecastList.isEmpty() ? 0 : forecastList.get(forecastList.size() - 1).getId();
    }

    @Override
    public List<Forecast> getForecastList() {
        return new ArrayList<>(forecastList);
    }

    @Override
    public void addItemsToNewsList(List<News> list) {
        newsList.addAll(list);
    }

    @Override
    public int getNewsListSize() {
        return newsList.size();
    }

    @Override
    public int getLastNewsId() {
        return newsList.isEmpty() ? 0 : newsList.get(newsList.size() - 1).getId() - 1;
    }

    @Override
    public List<News> getNewsList() {
        return new ArrayList<>(newsList);
    }

    @Override
    public void putNewInfograf(int id, Infograf infograf) {
        infografMap.put(id, infograf);
    }

    @Override
    public Infograf getInfografById(int id) {
        return infografMap.get(id);
    }

    @Override
    public News getNewsById(int id) {
        return Stream.of(newsList).filter(n -> n.getId() == id).single();
    }

    @Override
    public Forecast getForecastById(int id) {
        return Stream.of(forecastList).filter(n -> n.getId() == id).single();
    }

    @Override
    public Observable<Infograf> getInfografObservableById(int id) {
        Infograf infograf = getInfografById(id);
        return infograf != null ? Observable.just(infograf) :
                remoteRepository.getInfografById(id)
                        .map(BaseResponseObject::getData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<News>> getNewsPagingObservable(int offset, int limit) {
        return remoteRepository.getNewsListPaging(offset, limit)
                .map(BaseResponseArray::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Forecast>> getForecastPagingObservable(int offset, int limit) {
        return remoteRepository.getForecastListPaging(offset, limit)
                .map(BaseResponseArray::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}