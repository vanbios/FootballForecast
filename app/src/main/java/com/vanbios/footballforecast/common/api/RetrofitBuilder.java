package com.vanbios.footballforecast.common.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ihor Bilous
 */

class RetrofitBuilder {

    private static final String BASE_URL = "http://m.ratingbet.com/api/v1/";

    @NonNull
    static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
}
