package com.vanbios.footballforecast.common.utils.pagination;

/**
 * @author Ihor Bilous
 */

class PagingException extends RuntimeException {

    PagingException(String detailMessage) {
        super(detailMessage);
    }
}
