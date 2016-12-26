package com.vanbios.footballforecast.common.repository.remote;

import com.vanbios.footballforecast.common.api.RestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    public RemoteRepository provideRemoteRepository(RestApi restApi) {
        return new RemoteRepositoryImpl(restApi);
    }
}