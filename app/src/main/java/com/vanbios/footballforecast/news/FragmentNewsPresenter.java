package com.vanbios.footballforecast.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.utils.date.DateManager;
import com.vanbios.footballforecast.common.utils.pagination.PaginationTool;
import com.vanbios.footballforecast.news.models.News;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Ihor Bilous
 */

class FragmentNewsPresenter implements FragmentNewsMVP.Presenter {

    private final static int LIMIT = 20;
    private final static int INIT_LIST_COUNT = 1;
    private final static int RETRY_COUNT = 3;

    private FragmentNewsMVP.Model model;
    private FragmentNewsMVP.View view;
    private Subscription pagingSubscription;
    private RecyclerView recyclerView;
    private DateManager dateManager;
    private Context context;


    FragmentNewsPresenter(FragmentNewsMVP.Model model, DateManager dateManager, Context context) {
        this.model = model;
        this.dateManager = dateManager;
        this.context = context;
    }


    @Override
    public void setView(FragmentNewsMVP.View view) {
        this.view = view;
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
    public void refreshData() {
        unsubscribeRxPagination();

        model.getNewsPagingObservable(0, LIMIT)
                .subscribe(new Subscriber<List<News>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.showErrorMessage(context.getString(R.string.check_your_internet_connection));
                            view.refreshData(null);
                        }

                        pagingSubscription = getPagingSubscription();
                    }

                    @Override
                    public void onNext(List<News> items) {
                        model.setNewsList(items);

                        if (view != null) {
                            view.refreshData(newsListToViewModelList(model.getNewsList()));
                        }

                        pagingSubscription = getPagingSubscription();
                    }
                });
    }

    @Override
    public void loadInitialData(int count) {
        if (count < model.getNewsListSize() && view != null) {
            view.setInitialData(newsListToViewModelList(model.getNewsList()));
        }
    }

    @Override
    public int getNewsIdForPaging() {
        return model.getNewsIdForPaging();
    }

    private Subscription getPagingSubscription() {
        return PaginationTool.buildPagingObservable(recyclerView, offset ->
                model.getNewsPagingObservable(offset, LIMIT))
                .setLimit(LIMIT)
                .setEmptyListCount(INIT_LIST_COUNT)
                .setRetryCount(RETRY_COUNT)
                .build()
                .getPagingObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<News>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (model.getNewsListSize() == 0 && view != null) {
                            view.showErrorMessage(context.getString(R.string.check_your_internet_connection));
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<News> items) {
                        if (view != null) {
                            view.addMoreData(newsListToViewModelList(items));
                        }

                        model.addItemsToNewsList(items);
                    }
                });
    }

    private List<NewsViewModel> newsListToViewModelList(List<News> newsList) {
        return Stream.of(newsList).map(this::newsToViewModel).collect(Collectors.toList());
    }

    private NewsViewModel newsToViewModel(News news) {
        NewsViewModel.NewsViewModelBuilder builder = NewsViewModel.builder();
        if (news != null) {
            builder.id(news.getId());
            if (news.isPreviewValid()) {
                builder.title(news.getPreview());
            }
            if (news.isImageValid()) {
                builder.imageUrl(news.getImage());
            }
            if (news.isDateValid()) {
                long date = dateManager.dateToMillis(news.getDate());
                builder.time(dateManager.longTimeToString(date));
                builder.date(dateManager.longShortDateToString(date));
            }
        }
        return builder.build();
    }
}