package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    double volume;

    public Rain(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }
}
