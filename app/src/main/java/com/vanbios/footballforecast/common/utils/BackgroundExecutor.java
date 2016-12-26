package com.vanbios.footballforecast.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ihor Bilous
 */

public class BackgroundExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(128);

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    private static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue);

    public static Executor getSafeBackgroundExecutor() {
        return THREAD_POOL_EXECUTOR;
    }

    /*public static <T> Observable<T> createSafeBackgroundObservable(Observable.OnSubscribe<T> f) {
        return Observable.create(f).subscribeOn(Schedulers.from(THREAD_POOL_EXECUTOR));
    }*/
}
