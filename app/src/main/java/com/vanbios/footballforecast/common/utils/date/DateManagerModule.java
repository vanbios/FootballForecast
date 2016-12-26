package com.vanbios.footballforecast.common.utils.date;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class DateManagerModule {

    @Provides
    public DateManager provideDateManager() {
        return new DateManagerImpl();
    }
}
