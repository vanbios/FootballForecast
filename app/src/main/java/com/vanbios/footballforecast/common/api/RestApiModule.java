package com.vanbios.footballforecast.common.api;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author Ihor Bilous
 */

@Module
public class RestApiModule {

    @Provides
    public Retrofit provideRetrofit() {
        return RetrofitBuilder.getRetrofit();
    }

    @Provides
    public RestApi provideRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }
}
