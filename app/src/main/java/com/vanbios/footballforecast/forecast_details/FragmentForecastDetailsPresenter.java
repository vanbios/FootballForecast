package com.vanbios.footballforecast.forecast_details;

import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;

import rx.Subscriber;

/**
 * @author Ihor Bilous
 */

class FragmentForecastDetailsPresenter implements FragmentForecastDetailsMVP.Presenter {

    private FragmentForecastDetailsMVP.Model model;
    private FragmentForecastDetailsMVP.View view;

    FragmentForecastDetailsPresenter(FragmentForecastDetailsMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(FragmentForecastDetailsMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int id) {
        if (id > 0) {
            Forecast forecast = model.getForecastById(id);
            if (forecast != null) {
                if (view != null) {
                    view.showForecast(forecast);
                }

                if (forecast.getInfograf()) {
                    model.getInfografObservableById(id)
                            .subscribe(new Subscriber<Infograf>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Infograf infograf) {
                                    if (view != null) {
                                        view.showInfograf(infograf);
                                    }

                                    model.cacheLoadedInfograf(id, infograf);
                                }
                            });
                }
            }
        }
    }
}