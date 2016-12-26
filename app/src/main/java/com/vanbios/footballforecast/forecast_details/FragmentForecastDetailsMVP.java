package com.vanbios.footballforecast.forecast_details;

import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface FragmentForecastDetailsMVP {

    interface Model {

        Forecast getForecastById(int id);

        Observable<Infograf> getInfografObservableById(int id);

        void cacheLoadedInfograf(int id, Infograf infograf);
    }

    interface View {

        void showForecast(Forecast forecast);

        void showInfograf(Infograf infograf);
    }

    interface Presenter {

        void setView(FragmentForecastDetailsMVP.View view);

        void loadData(int id);
    }
}
