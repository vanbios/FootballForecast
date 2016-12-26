package com.vanbios.footballforecast.forecast.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ihor Bilous
 */

@Getter
@Setter
@ToString
public class Forecast implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("hot")
    @Expose
    private Integer hot;
    @SerializedName("hotDay")
    @Expose
    private Integer hotDay;
    @SerializedName("inforgaf")
    @Expose
    private Boolean infograf;
    @SerializedName("tournament")
    @Expose
    private String tournament;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("t1name")
    @Expose
    private String t1name;
    @SerializedName("t2name")
    @Expose
    private String t2name;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("textKoef")
    @Expose
    private String textKoef;
    @SerializedName("sname")
    @Expose
    private String sname;


    public boolean isHotValid() {
        return hot != null;
    }

    public boolean isDateValid() {
        return date != null;
    }

    public boolean isInfogrValid() {
        return infograf != null;
    }

    public boolean isImageValid() {
        return image != null;
    }

    public boolean isTextCoeffValid() {
        return textKoef != null;
    }

    public boolean isUrlValid() {
        return url != null;
    }
}
