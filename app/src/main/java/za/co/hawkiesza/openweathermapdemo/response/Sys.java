package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("type")
    double type;
    @SerializedName("id")
    double id;
    @SerializedName("message")
    double message;
    @SerializedName("country")
    String country;
    @SerializedName("sunrise")
    long sunriseTime;
    @SerializedName("sunset")
    long sunsetTime;

    public Sys(double type, double id, double message, String country, long sunriseTime, long sunsetTime) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public double getType() {
        return type;
    }

    public double getId() {
        return id;
    }

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }
}
