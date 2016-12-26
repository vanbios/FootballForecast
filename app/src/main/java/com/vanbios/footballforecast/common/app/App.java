package com.vanbios.footballforecast.common.app;

import android.app.Application;
import android.content.Context;

import lombok.Getter;


/**
 * @author Ihor Bilous
 */
public class App extends Application {

    @Getter
    private AppComponent component;


    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
