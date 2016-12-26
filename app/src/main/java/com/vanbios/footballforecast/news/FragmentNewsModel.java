package com.vanbios.footballforecast.news;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.news.models.News;

import java.util.List;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

class FragmentNewsModel implements FragmentNewsMVP.Model {

    private MemoryRepository memoryRepository;


    FragmentNewsModel(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public Observable<List<News>> getNewsPagingObservable(int offset, int limit) {
        return memoryRepository.getNewsPagingObservable(offset, limit);
    }

    @Override
    public void setNewsList(List<News> list) {
        memoryRepository.setNewsList(list);
    }

    @Override
    public List<News> getNewsList() {
        return memoryRepository.getNewsList();
    }

    @Override
    public int getNewsListSize() {
        return memoryRepository.getNewsListSize();
    }

    @Override
    public void addItemsToNewsList(List<News> list) {
        memoryRepository.addItemsToNewsList(list);
    }

    @Override
    public int getNewsIdForPaging() {
        return memoryRepository.getLastNewsId();
    }
}