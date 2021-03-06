package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse
{
    @SerializedName("coord")
    private Coord coord;
    @SerializedName("weather")
    private List<Weather> weatherList;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private MainInfo main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("rain")
    private Rain rain;
    @SerializedName("snow")
    private Snow snow;
    @SerializedName("dt")
    private long dateTime;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String cityName;
    @SerializedName("cod")
    private double cod;

    public WeatherResponse(Coord coord, List<Weather> weatherList, String base, MainInfo main, Wind wind, Clouds clouds, Rain rain, Snow snow, long dateTime, Sys sys, int id, String cityName, double cod)
    {
        this.coord = coord;
        this.weatherList = weatherList;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
        this.dateTime = dateTime;
        this.sys = sys;
        this.id = id;
        this.cityName = cityName;
        this.cod = cod;
    }

    public Coord getCoord()
    {
        return coord;
    }

    public List<Weather> getWeatherList()
    {
        return weatherList;
    }

    public String getBase()
    {
        return base;
    }

    public MainInfo getMain()
    {
        return main;
    }

    public Wind getWind()
    {
        return wind;
    }

    public Clouds getClouds()
    {
        return clouds;
    }

    public Rain getRain()
    {
        return rain;
    }

    public Snow getSnow()
    {
        return snow;
    }

    public long getDateTime()
    {
        return dateTime;
    }

    public Sys getSys()
    {
        return sys;
    }

    public int getId()
    {
        return id;
    }

    public String getCityName()
    {
        return cityName;
    }

    public double getCod()
    {
        return cod;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(MainInfo main) {
        this.main = main;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }
}
