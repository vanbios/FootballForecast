package com.vanbios.footballforecast.common.repository.memory;

import com.vanbios.footballforecast.common.repository.remote.RemoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ihor Bilous
 */

@Module
public class MemoryRepositoryModule {

    @Provides
    @Singleton
    public MemoryRepository provideMemotrRepository(RemoteRepository remoteRepository) {
        return new MemoryRepositoryImpl(remoteRepository);
    }
}
