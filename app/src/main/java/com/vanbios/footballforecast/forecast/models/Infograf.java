package com.vanbios.footballforecast.forecast.models;

import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Ihor Bilous
 */

@Getter
@ToString
public class Infograf {

    @SerializedName("attr_75")
    @Expose
    public String attr75;
    @SerializedName("attr_74")
    @Expose
    public String attr74;
    @SerializedName("attr_73")
    @Expose
    public String attr73;
    @SerializedName("attr_72")
    @Expose
    public String attr72;
    @SerializedName("attr_71")
    @Expose
    public String attr71;
    @SerializedName("attr_70")
    @Expose
    public String attr70;
    @SerializedName("attr_69")
    @Expose
    public String attr69;
    @SerializedName("attr_68")
    @Expose
    public String attr68;
    @SerializedName("attr_67")
    @Expose
    public String attr67;
    @SerializedName("attr_66")
    @Expose
    public String attr66;
    @SerializedName("attr_65")
    @Expose
    public String attr65;
    @SerializedName("attr_64")
    @Expose
    public String attr64;
    @SerializedName("attr_63")
    @Expose
    public String attr63;
    @SerializedName("attr_62")
    @Expose
    public String attr62;
    @SerializedName("attr_61")
    @Expose
    public String attr61;
    @SerializedName("attr_60")
    @Expose
    public String attr60;
    @SerializedName("attr_59")
    @Expose
    public String attr59;
    @SerializedName("attr_58")
    @Expose
    public String attr58;
    @SerializedName("attr_57")
    @Expose
    public String attr57;
    @SerializedName("attr_56")
    @Expose
    public String attr56;
    @SerializedName("attr_55")
    @Expose
    public String attr55;
    @SerializedName("attr_54")
    @Expose
    public String attr54;
    @SerializedName("attr_53")
    @Expose
    public String attr53;
    @SerializedName("attr_52")
    @Expose
    public String attr52;
    @SerializedName("attr_51")
    @Expose
    public String attr51;
    @SerializedName("attr_50")
    @Expose
    public String attr50;
    @SerializedName("attr_49")
    @Expose
    public String attr49;
    @SerializedName("attr_48")
    @Expose
    public String attr48;
    @SerializedName("attr_47")
    @Expose
    public String attr47;
    @SerializedName("attr_46")
    @Expose
    public String attr46;
    @SerializedName("attr_45")
    @Expose
    public String attr45;
    @SerializedName("attr_44")
    @Expose
    public String attr44;
    @SerializedName("attr_43")
    @Expose
    public String attr43;
    @SerializedName("attr_42")
    @Expose
    public String attr42;
    @SerializedName("attr_41")
    @Expose
    public String attr41;
    @SerializedName("attr_40")
    @Expose
    public String attr40;
    @SerializedName("attr_39")
    @Expose
    public String attr39;
    @SerializedName("attr_38")
    @Expose
    public String attr38;
    @SerializedName("attr_37")
    @Expose
    public String attr37;
    @SerializedName("attr_36")
    @Expose
    public String attr36;
    @SerializedName("attr_35")
    @Expose
    public String attr35;
    @SerializedName("attr_34")
    @Expose
    public String attr34;
    @SerializedName("attr_33")
    @Expose
    public String attr33;
    @SerializedName("attr_32")
    @Expose
    public String attr32;
    @SerializedName("attr_31")
    @Expose
    public String attr31;
    @SerializedName("attr_30")
    @Expose
    public String attr30;
    @SerializedName("attr_29")
    @Expose
    public String attr29;
    @SerializedName("attr_28")
    @Expose
    public String attr28;
    @SerializedName("attr_27")
    @Expose
    public String attr27;
    @SerializedName("attr_26")
    @Expose
    public String attr26;
    @SerializedName("attr_25")
    @Expose
    public String attr25;
    @SerializedName("attr_24")
    @Expose
    public String attr24;
    @SerializedName("attr_23")
    @Expose
    public String attr23;
    @SerializedName("attr_22")
    @Expose
    public String attr22;
    @SerializedName("attr_21")
    @Expose
    public String attr21;
    @SerializedName("attr_20")
    @Expose
    public String attr20;
    @SerializedName("attr_19")
    @Expose
    public String attr19;
    @SerializedName("attr_18")
    @Expose
    public String attr18;
    @SerializedName("attr_17")
    @Expose
    public String attr17;
    @SerializedName("attr_16")
    @Expose
    public String attr16;
    @SerializedName("attr_15")
    @Expose
    public String attr15;
    @SerializedName("attr_14")
    @Expose
    public String attr14;
    @SerializedName("attr_13")
    @Expose
    public String attr13;
    @SerializedName("attr_12")
    @Expose
    public String attr12;
    @SerializedName("attr_11")
    @Expose
    public String attr11;
    @SerializedName("attr_10")
    @Expose
    public String attr10;
    @SerializedName("attr_9")
    @Expose
    public String attr9;


    private boolean isValidTournamentSeasonStatistic() {
        return attr17 != null && attr18 != null && attr19 != null && attr20 != null
                && attr21 != null && attr22 != null && attr23 != null && attr24 != null
                && attr25 != null && attr26 != null && attr27 != null && attr28 != null
                && attr29 != null && attr30 != null && attr31 != null && attr32 != null
                && attr33 != null && attr34 != null;
    }

    private boolean isValidAdditionalStatistic() {
        return attr12 != null && attr67 != null && attr68 != null;
    }

    public boolean hasAdditionalStatistic() {
        return attr69 != null && Integer.valueOf(attr69) > 0 && isValidAdditionalStatistic();
    }

    public boolean hasTournamentSeasonStatistic() {
        return attr70 != null && Integer.valueOf(attr70) > 0 && isValidTournamentSeasonStatistic();
    }

    private boolean isValidGoalsStatistic() {
        return attr35 != null && attr36 != null && attr37 != null && attr38 != null
                && attr39 != null && attr40 != null && attr41 != null && attr42 != null
                && attr43 != null && attr44 != null && attr45 != null && attr46 != null;
    }

    public boolean hasGoalsStatistic() {
        return attr71 != null && Integer.valueOf(attr71) > 0 && isValidGoalsStatistic();
    }

    private boolean isValidAverageAgeStatistic() {
        return attr53 != null && attr54 != null;
    }

    public boolean hasAverageAge() {
        return attr73 != null && Integer.valueOf(attr73) > 0 && isValidAverageAgeStatistic();
    }

    public Pair<Double, Double> getAverageAgePair() {
        return new Pair<>(Double.valueOf(attr53), Double.valueOf(attr54));
    }

    private boolean isValidAverageProgressStatistic() {
        return attr47 != null && attr48 != null
                && attr49 != null && attr50 != null
                && attr51 != null && attr52 != null;
    }

    public boolean hasAverageProgress() {
        return attr72 != null && Integer.valueOf(attr72) > 0 && isValidAverageProgressStatistic();
    }

    public double[] getAverageProgress() {
        return new double[]{Double.valueOf(attr51), Double.valueOf(attr52), Double.valueOf(attr49),
                Double.valueOf(attr50), Double.valueOf(attr47), Double.valueOf(attr48)};
    }
}
