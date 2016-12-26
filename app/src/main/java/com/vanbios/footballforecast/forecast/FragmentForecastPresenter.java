package com.vanbios.footballforecast.forecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.utils.date.DateManager;
import com.vanbios.footballforecast.common.utils.pagination.PaginationTool;
import com.vanbios.footballforecast.forecast.comparators.ForecastDateDownComparator;
import com.vanbios.footballforecast.forecast.comparators.ForecastDateUpComparator;
import com.vanbios.footballforecast.forecast.enums.ForecastTypeEnum;
import com.vanbios.footballforecast.forecast.models.Forecast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Ihor Bilous
 */

class FragmentForecastPresenter implements FragmentForecastMVP.Presenter {

    private final static int LIMIT = 20;
    private final static int RETRY_COUNT = 3;

    private FragmentForecastMVP.View view;
    private FragmentForecastMVP.Model model;
    private Subscription pagingSubscription;
    private RecyclerView recyclerView;
    private Context context;
    private DateManager dateManager;

    private int filterType;
    private int filterDate;


    FragmentForecastPresenter(FragmentForecastMVP.Model model, DateManager dateManager, Context context) {
        this.model = model;
        this.dateManager = dateManager;
        this.context = context;
    }

    @Override
    public void setView(FragmentForecastMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadFilteredData(int count, int filterType, int filterDate) {
        this.filterType = filterType;
        this.filterDate = filterDate;

        if (count < model.getForecastListSize()) {
            sortItemsListAndAddToViewAndRepository(model.getForecastList(), false, false, true);
        }
    }

    @Override
    public void refreshFilteredData(int filterType, int filterDate) {
        this.filterType = filterType;
        this.filterDate = filterDate;

        unsubscribeRxPagination();

        model.getForecastPagingObservable(0, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Forecast>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pagingSubscription = getPagingSubscription();
                        if (view != null) {
                            view.showErrorMessage(context.getString(R.string.check_your_internet_connection));
                            view.refreshFilteredData(null);
                        }
                    }

                    @Override
                    public void onNext(List<Forecast> items) {
                        sortItemsListAndAddToViewAndRepository(items, true, true, false);
                    }
                });
    }

    @Override
    public void subscribeRxPagination(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        pagingSubscription = getPagingSubscription();
    }

    @Override
    public void unsubscribeRxPagination() {
        if (isRxSubscribed()) pagingSubscription.unsubscribe();
    }

    private boolean isRxSubscribed() {
        return pagingSubscription != null && !pagingSubscription.isUnsubscribed();
    }

    @Override
    public int getForecastIdForPaging() {
        return model.getForecastIdForPaging();
    }

    private Subscription getPagingSubscription() {
        return PaginationTool.buildPagingObservable(recyclerView, offset ->
                model.getForecastPagingObservable(offset, LIMIT))
                .setLimit(LIMIT)
                .setRetryCount(RETRY_COUNT)
                .build()
                .getPagingObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Forecast>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (model.getForecastListSize() == 0 && view != null) {
                            view.showErrorMessage(context.getString(R.string.check_your_internet_connection));
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Forecast> items) {
                        sortItemsListAndAddToViewAndRepository(items, true, false, false);
                    }
                });
    }

    private void sortItemsListAndAddToViewAndRepository(List<Forecast> list, boolean addToRepository, boolean refresh, boolean setNewData) {
        if (addToRepository) {
            if (refresh) {
                model.setForecastList(list);
            } else {
                model.addItemsToForecastList(list);
            }
        }

        Observable.<List<ForecastViewModel>>create(subscriber -> {
            boolean withForecastOfTheDay = false;
            if (model.getForecastListSize() <= LIMIT || !addToRepository) {
                withForecastOfTheDay = true;
                long curSec = dateManager.getCurrentSeconds();
                List<Forecast> futureList = Stream.of(list.subList(0, LIMIT))
                        .filter(p -> p.getDate() >= curSec)
                        .collect(Collectors.toList());
                List<Forecast> pastList = new ArrayList<>(list);
                pastList.removeAll(futureList);

                Collections.sort(futureList, new ForecastDateUpComparator());
                Collections.sort(pastList, new ForecastDateDownComparator());

                List<Forecast> prOfDayList = Stream.of(futureList)
                        .filter(p -> p.getHotDay() > 0)
                        .collect(Collectors.toList());

                futureList.add(0, prOfDayList.isEmpty() ? futureList.get(0) : prOfDayList.get(0));

                list.clear();
                list.addAll(futureList);
                list.addAll(pastList);
            } else {
                Collections.sort(list, new ForecastDateDownComparator());
            }

            List<Forecast> filteredList = formFilteredList(list, withForecastOfTheDay);
            List<ForecastViewModel> result = forecastListToViewModelList(filteredList);

            subscriber.onNext(result);
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<ForecastViewModel>>() {

                            @Override
                            public void onNext(List<ForecastViewModel> resultList) {
                                if (refresh) {
                                    pagingSubscription = getPagingSubscription();

                                    if (view != null) {
                                        view.refreshFilteredData(resultList);
                                    }
                                } else if (setNewData) {
                                    if (view != null) {
                                        view.setFilteredData(resultList);
                                    }
                                } else {
                                    if (view != null) {
                                        view.addMoreFilteredData(resultList);
                                    }
                                }
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }
                );
    }

    private List<Forecast> formFilteredList(List<Forecast> originalList, boolean withForecastOfTheDay) {
        List<Forecast> result = new ArrayList<>();
        if (originalList.isEmpty()) return result;

        int startPos = withForecastOfTheDay ? 1 : 0;
        List<Forecast> tmpList = new ArrayList<>(originalList.subList(startPos, originalList.size()));
        if (filterDate != 0) {
            long start = filterDate == 1 ? dateManager.getTodayStartTime() : dateManager.getTomorrowStartTime();
            long end = filterDate == 1 ? dateManager.getTodayEndTime() : dateManager.getTomorrowEndTime();

            List<Forecast> fByDateList = Stream.of(tmpList)
                    .filter(p -> dateManager.dateToMillis(p.getDate()) < end && dateManager.dateToMillis(p.getDate()) > start)
                    .collect(Collectors.toList());

            tmpList.clear();
            tmpList.addAll(fByDateList);
        }

        if (withForecastOfTheDay) {
            result.add(originalList.get(0));
        }
        if (filterType == 0) result.addAll(tmpList);
        else {
            String filterQuery = null;
            switch (filterType) {
                case 1:
                    filterQuery = ForecastTypeEnum.SOCCER.getTitle();
                    break;
                case 2:
                    filterQuery = ForecastTypeEnum.HOCKEY.getTitle();
                    break;
                case 3:
                    filterQuery = ForecastTypeEnum.BASKETBALL.getTitle();
                    break;
                case 4:
                    filterQuery = ForecastTypeEnum.TENNIS.getTitle();
                    break;
            }
            if (filterQuery != null) {
                final String s = filterQuery;
                result.addAll(Stream.of(tmpList)
                        .filter(p -> p.getSname().equals(s))
                        .collect(Collectors.toList()));
            } else {
                result.addAll(tmpList);
            }
        }
        return result;
    }

    private List<ForecastViewModel> forecastListToViewModelList(List<Forecast> forecastList) {
        return Stream.of(forecastList).map(this::forecastToViewModel).collect(Collectors.toList());
    }

    private ForecastViewModel forecastToViewModel(Forecast forecast) {
        ForecastViewModel.ForecastViewModelBuilder builder = ForecastViewModel.builder();
        if (forecast != null) {
            builder.id(forecast.getId());
            builder.title(
                    String.format(Locale.UK,
                            "%s - %s",
                            forecast.getT1name(),
                            forecast.getT2name()));
            if (forecast.isDateValid()) {
                builder.subTitle(
                        String.format(Locale.getDefault(),
                                context.getString(R.string.forecast_for_date_placeholder),
                                dateManager.longFullDateToString(dateManager.dateToMillis(forecast.getDate()))));
            }
            if (forecast.isImageValid() && forecast.getImage().isSrcValid()) {
                builder.imageUrl(forecast.getImage().getSrc());
            }
            if (forecast.isHotValid()) {
                builder.isHot(forecast.getHot() > 0);
            }
            if (forecast.isInfogrValid()) {
                builder.hasInfograf(forecast.getInfograf());
            }
            if (forecast.isTextCoeffValid()) {
                builder.textKoef(forecast.getTextKoef());
            }
        }
        return builder.build();
    }
}