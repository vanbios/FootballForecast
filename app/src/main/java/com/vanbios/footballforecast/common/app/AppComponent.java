package com.vanbios.footballforecast.common.app;

import com.vanbios.footballforecast.common.api.RestApiModule;
import com.vanbios.footballforecast.common.repository.memory.MemoryRepositoryModule;
import com.vanbios.footballforecast.common.repository.remote.RemoteRepositoryModule;
import com.vanbios.footballforecast.common.ui.activities.MainActivity;
import com.vanbios.footballforecast.common.utils.date.DateManagerModule;
import com.vanbios.footballforecast.common.utils.ui.ToastManagerModule;
import com.vanbios.footballforecast.forecast.FragmentForecast;
import com.vanbios.footballforecast.forecast.FragmentForecastModule;
import com.vanbios.footballforecast.forecast_details.FragmentForecastDetails;
import com.vanbios.footballforecast.forecast_details.FragmentForecastDetailsModule;
import com.vanbios.footballforecast.news.FragmentNews;
import com.vanbios.footballforecast.news.FragmentNewsModule;
import com.vanbios.footballforecast.news_details.FragmentNewsDetails;
import com.vanbios.footballforecast.news_details.FragmentNewsDetailsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Ihor Bilous
 */

@Singleton
@Component(modules = {
        AppModule.class,
        MemoryRepositoryModule.class,
        RemoteRepositoryModule.class,
        RestApiModule.class,
        ToastManagerModule.class,
        DateManagerModule.class,
        FragmentNewsModule.class,
        FragmentNewsDetailsModule.class,
        FragmentForecastModule.class,
        FragmentForecastDetailsModule.class
})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(FragmentForecast fragmentForecast);

    void inject(FragmentNews fragmentNews);

    void inject(FragmentForecastDetails fragmentForecastDetails);

    void inject(FragmentNewsDetails fragmentNewsDetails);
}
