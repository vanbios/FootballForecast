package com.vanbios.footballforecast.forecast;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.forecast.models.Forecast;

import java.util.List;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

class FragmentForecastModel implements FragmentForecastMVP.Model {

    private MemoryRepository memoryRepository;


    FragmentForecastModel(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }


    @Override
    public Observable<List<Forecast>> getForecastPagingObservable(int offset, int limit) {
        return memoryRepository.getForecastPagingObservable(offset, limit);
    }

    @Override
    public void setForecastList(List<Forecast> list) {
        memoryRepository.setForecastList(list);
    }

    @Override
    public List<Forecast> getForecastList() {
        return memoryRepository.getForecastList();
    }

    @Override
    public int getForecastListSize() {
        return memoryRepository.getForecastListSize();
    }

    @Override
    public void addItemsToForecastList(List<Forecast> list) {
        memoryRepository.addItemsToForecastList(list);
    }

    @Override
    public int getForecastIdForPaging() {
        return memoryRepository.getLastForecastId();
    }
}