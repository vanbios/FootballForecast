package com.vanbios.footballforecast.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author Ihor Bilous
 */

@Getter
public class News implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("date")
    @Expose
    public Integer date;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("preview")
    @Expose
    public String preview;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("html")
    @Expose
    public String html;
    @SerializedName("imageWidth")
    @Expose
    public Integer imageWidth;
    @SerializedName("imageHeight")
    @Expose
    public Integer imageHeight;


    public boolean isPreviewValid() {
        return preview != null;
    }

    public boolean isDateValid() {
        return date != null;
    }

    public boolean isImageValid() {
        return image != null;
    }

    public boolean isHtmlValid() {
        return html != null;
    }

    public boolean isUrlValid() {
        return url != null;
    }
}
