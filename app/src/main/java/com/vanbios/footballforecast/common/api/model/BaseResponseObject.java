package com.vanbios.footballforecast.common.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Getter
public class BaseResponseObject<T> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private T data;

}