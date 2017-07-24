package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("cnt")
    private int count;
    @SerializedName("list")
    private List<Forecast> forecastList;
    @SerializedName("city")
    private City city;
    @SerializedName("country")
    private String country;

    public ForecastResponse(int count, List<Forecast> forecastList, City city, String country) {
        this.count = count;
        this.forecastList = forecastList;
        this.city = city;
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
