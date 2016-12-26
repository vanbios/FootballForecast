package com.vanbios.footballforecast.news_details;

import com.vanbios.footballforecast.news.models.News;

/**
 * @author Ihor Bilous
 */

public interface FragmentNewsDetailsMVP {

    interface Model {

        News getNewsById(int id);
    }

    interface View {

        void setData(NewsDetailsViewModel newsDetailsViewModel);
    }

    interface Presenter {

        void setView(FragmentNewsDetailsMVP.View view);

        void loadData(int id);
    }
}