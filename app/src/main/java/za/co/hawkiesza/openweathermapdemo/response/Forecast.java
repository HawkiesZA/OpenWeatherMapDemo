package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    @SerializedName("dt")
    private long dateTime;
    @SerializedName("main")
    private MainInfo main;
    @SerializedName("weather")
    private List<Weather> weatherList;
    @SerializedName("base")
    private String base;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("rain")
    private Rain rain;
    @SerializedName("snow")
    private Snow snow;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("dt_txt")
    private String dateTimeText;

    public Forecast(long dateTime, MainInfo main, List<Weather> weatherList, String base, Clouds clouds, Wind wind, Rain rain, Snow snow, Sys sys, String dateTimeText) {
        this.dateTime = dateTime;
        this.main = main;
        this.weatherList = weatherList;
        this.base = base;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.sys = sys;
        this.dateTimeText = dateTimeText;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public MainInfo getMain() {
        return main;
    }

    public void setMain(MainInfo main) {
        this.main = main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDateTimeText() {
        return dateTimeText;
    }

    public void setDateTimeText(String dateTimeText) {
        this.dateTimeText = dateTimeText;
    }
}
