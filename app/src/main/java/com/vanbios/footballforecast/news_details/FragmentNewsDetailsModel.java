package com.vanbios.footballforecast.news_details;

import com.vanbios.footballforecast.common.repository.memory.MemoryRepository;
import com.vanbios.footballforecast.news.models.News;

/**
 * @author Ihor Bilous
 */

class FragmentNewsDetailsModel implements FragmentNewsDetailsMVP.Model {

    private MemoryRepository memoryRepository;

    FragmentNewsDetailsModel(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public News getNewsById(int id) {
        return memoryRepository.getNewsById(id);
    }
}