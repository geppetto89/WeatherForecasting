
package android.mobile.micmen.nicedayforecasting.network.dto;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Rain {

    @SerializedName("3h")
    private Double rain3h;

    public Rain(Double rain3h) {
        this.rain3h = rain3h;
    }

    public Double getRain3h() {
        return rain3h;
    }

    public void setRain3h(Double rain3h) {
        this.rain3h = rain3h;
    }
}
