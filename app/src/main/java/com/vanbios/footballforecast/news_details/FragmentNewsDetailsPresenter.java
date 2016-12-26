package com.vanbios.footballforecast.news_details;

import com.vanbios.footballforecast.common.utils.date.DateManager;
import com.vanbios.footballforecast.news.models.News;

/**
 * @author Ihor Bilous
 */

class FragmentNewsDetailsPresenter implements FragmentNewsDetailsMVP.Presenter {

    private FragmentNewsDetailsMVP.Model model;
    private FragmentNewsDetailsMVP.View view;
    private DateManager dateManager;


    FragmentNewsDetailsPresenter(FragmentNewsDetailsMVP.Model model, DateManager dateManager) {
        this.model = model;
        this.dateManager = dateManager;
    }

    @Override
    public void setView(FragmentNewsDetailsMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int id) {
        if (view != null) {
            view.setData(newsToViewModel(model.getNewsById(id)));
        }
    }

    private NewsDetailsViewModel newsToViewModel(News news) {
        NewsDetailsViewModel.NewsDetailsViewModelBuilder builder = NewsDetailsViewModel.builder();
        if (news != null) {
            if (news.isImageValid()) {
                builder.imageUrl(news.getImage());
            }
            if (news.isDateValid()) {
                long date = dateManager.dateToMillis(news.getDate());
                builder.time(dateManager.longTimeToString(date));
                builder.date(dateManager.longShortDateToString(date));
            }
            if (news.isHtmlValid()) {
                builder.html(news.getHtml());
            }
            if (news.isUrlValid()) {
                builder.url(news.getUrl());
            }
        }
        return builder.build();
    }
}