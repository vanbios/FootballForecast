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
public class Author implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("html")
    @Expose
    private String html;


    public boolean isImageValid() {
        return image != null;
    }

    public boolean isHtmlValid() {
        return html != null;
    }

    public boolean isNameValid() {
        return name != null;
    }

}
