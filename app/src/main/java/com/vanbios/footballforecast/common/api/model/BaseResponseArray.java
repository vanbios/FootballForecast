package com.vanbios.footballforecast.common.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ihor Bilous
 */

@Getter
@Setter
public class BaseResponseArray<T> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<T> data = new ArrayList<>();

    public BaseResponseArray(List<T> data) {
        this.data = data;
    }

}