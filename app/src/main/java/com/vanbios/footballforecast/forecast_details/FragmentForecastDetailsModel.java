package com.vanbios.footballforecast.forecast_details;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

class FragmentForecastDetailsModel implements FragmentForecastDetailsMVP.Model {

    private MemoryRepository memoryRepository;

    FragmentForecastDetailsModel(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public Forecast getForecastById(int id) {
        return memoryRepository.getForecastById(id);
    }

    @Override
    public Observable<Infograf> getInfografObservableById(int id) {
        return memoryRepository.getInfografObservableById(id);
    }

    @Override
    public void cacheLoadedInfograf(int id, Infograf infograf) {
        if (infograf != null) {
            if (memoryRepository.getInfografById(id) == null) {
                memoryRepository.putNewInfograf(id, infograf);
            }
        }
    }
}
