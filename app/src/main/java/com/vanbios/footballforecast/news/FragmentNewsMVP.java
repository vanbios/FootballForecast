package com.vanbios.footballforecast.news;

import android.support.v7.widget.RecyclerView;

import com.vanbios.footballforecast.news.models.News;

import java.util.List;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface FragmentNewsMVP {

    interface Model {

        Observable<List<News>> getNewsPagingObservable(int offset, int limit);

        void setNewsList(List<News> list);

        List<News> getNewsList();

        int getNewsListSize();

        void addItemsToNewsList(List<News> list);

        int getNewsIdForPaging();
    }

    interface View {

        void setInitialData(List<NewsViewModel> newsViewModelList);

        void refreshData(List<NewsViewModel> newsViewModelList);

        void addMoreData(List<NewsViewModel> newsViewModelList);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void setView(FragmentNewsMVP.View view);

        void loadInitialData(int count);

        void refreshData();

        void subscribeRxPagination(RecyclerView recyclerView);

        void unsubscribeRxPagination();

        int getNewsIdForPaging();
    }
}