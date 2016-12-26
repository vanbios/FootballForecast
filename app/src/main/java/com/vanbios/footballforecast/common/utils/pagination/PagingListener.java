package com.vanbios.footballforecast.common.utils.pagination;

import rx.Observable;

/**
 * @author Ihor Bilous
 */

public interface PagingListener<T> {

    Observable<T> onNextPage(int offset);
}
