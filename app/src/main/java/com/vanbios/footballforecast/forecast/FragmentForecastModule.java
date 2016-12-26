package com.vanbios.footballforecast.forecast;

import android.content.Context;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.common.utils.date.DateManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class FragmentForecastModule {

    @Provides
    public FragmentForecastMVP.Model provideFragmentForecastModel(MemoryRepository memoryRepository) {
        return new FragmentForecastModel(memoryRepository);
    }

    @Provides
    public FragmentForecastMVP.Presenter provideFragmentForecastPresenter(FragmentForecastMVP.Model model, DateManager dateManager, Context context) {
        return new FragmentForecastPresenter(model, dateManager, context);
    }
}