package com.vanbios.footballforecast.news;

import android.content.Context;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.common.utils.date.DateManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class FragmentNewsModule {

    @Provides
    public FragmentNewsMVP.Model provideFragmentNewsModel(MemoryRepository memoryRepository) {
        return new FragmentNewsModel(memoryRepository);
    }

    @Provides
    public FragmentNewsMVP.Presenter provideFragmentNewsPresenter(FragmentNewsMVP.Model model, DateManager dateManager, Context context) {
        return new FragmentNewsPresenter(model, dateManager, context);
    }
}