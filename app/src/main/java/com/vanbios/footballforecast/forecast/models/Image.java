package com.vanbios.footballforecast.forecast.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ihor Bilous
 */

@Getter
@Setter
public class Image implements Serializable {

    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;

    public boolean isSrcValid() {
        return src != null;
    }

}
