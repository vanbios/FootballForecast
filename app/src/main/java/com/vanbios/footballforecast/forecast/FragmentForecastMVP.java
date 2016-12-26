package com.vanbios.footballforecast.forecast;

import android.support.v7.widget.RecyclerView;

import com.vanbios.footballforecast.forecast.models.Forecast;

import java.util.List;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

interface FragmentForecastMVP {

    interface Model {

        Observable<List<Forecast>> getForecastPagingObservable(int offset, int limit);

        void setForecastList(List<Forecast> list);

        List<Forecast> getForecastList();

        int getForecastListSize();

        void addItemsToForecastList(List<Forecast> list);

        int getForecastIdForPaging();
    }

    interface View {

        void setFilteredData(List<ForecastViewModel> forecastViewModelList);

        void refreshFilteredData(List<ForecastViewModel> forecastViewModelList);

        void addMoreFilteredData(List<ForecastViewModel> forecastViewModelList);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void setView(FragmentForecastMVP.View view);

        void loadFilteredData(int count, int filterType, int filterDate);

        void refreshFilteredData(int filterType, int filterDate);

        void subscribeRxPagination(RecyclerView recyclerView);

        void unsubscribeRxPagination();

        int getForecastIdForPaging();
    }
}