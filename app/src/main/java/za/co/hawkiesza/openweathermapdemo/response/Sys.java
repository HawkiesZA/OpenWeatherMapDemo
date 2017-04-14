package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Sys
{
    @SerializedName("type")
    private double type;
    @SerializedName("id")
    private double id;
    @SerializedName("message")
    private double message;
    @SerializedName("country")
    private String country;
    @SerializedName("sunrise")
    private long sunriseTime;
    @SerializedName("sunset")
    private long sunsetTime;

    public Sys(double type, double id, double message, String country, long sunriseTime, long sunsetTime)
    {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public double getType()
    {
        return type;
    }

    public double getId()
    {
        return id;
    }

    public double getMessage()
    {
        return message;
    }

    public String getCountry()
    {
        return country;
    }

    public long getSunriseTime()
    {
        return sunriseTime;
    }

    public long getSunsetTime()
    {
        return sunsetTime;
    }
}
