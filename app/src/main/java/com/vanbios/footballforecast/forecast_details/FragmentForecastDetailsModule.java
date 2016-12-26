package com.vanbios.footballforecast.forecast_details;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class FragmentForecastDetailsModule {

    @Provides
    public FragmentForecastDetailsMVP.Model provideFragmentForecastDetailsModel(MemoryRepository memoryRepository) {
        return new FragmentForecastDetailsModel(memoryRepository);
    }

    @Provides
    public FragmentForecastDetailsMVP.Presenter provideFragmentForecastDetailsPresenter(FragmentForecastDetailsMVP.Model model) {
        return new FragmentForecastDetailsPresenter(model);
    }
}