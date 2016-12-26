package com.vanbios.footballforecast.news_details;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.common.utils.date.DateManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class FragmentNewsDetailsModule {

    @Provides
    public FragmentNewsDetailsMVP.Model provideFragmentNewsModel(MemoryRepository memoryRepository) {
        return new FragmentNewsDetailsModel(memoryRepository);
    }

    @Provides
    public FragmentNewsDetailsMVP.Presenter provideFragmentNewsPresenter(FragmentNewsDetailsMVP.Model model, DateManager dateManager) {
        return new FragmentNewsDetailsPresenter(model, dateManager);
    }
}