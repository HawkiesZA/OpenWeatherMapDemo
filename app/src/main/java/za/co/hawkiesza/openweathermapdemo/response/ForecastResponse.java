package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import za.co.hawkiesza.openweathermapdemo.R;

public class ForecastResponse {
    @SerializedName("cnt")
    private int count;
    @SerializedName("list")
    private List<Forecast> forecastList;
    @SerializedName("city")
    private City city;
    @SerializedName("country")
    private String country;
    private int forecastIconId;

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

    public int getForecastDrawable()
    {
        Forecast forecast = this.forecastList.get(0);
        int weatherId = forecast.getWeatherList().get(0).getId();

        if (weatherId < 300) {
            forecastIconId = R.drawable.ic_thunderstorm;
        }
        else if (weatherId < 400) {
            forecastIconId = R.drawable.ic_drizzle;
        }
        else if (weatherId < 600) {
            forecastIconId = R.drawable.ic_rain;
        }
        else if (weatherId < 700) {
            forecastIconId = R.drawable.ic_snow;
        }
        else if (weatherId < 800) {
            forecastIconId = R.drawable.ic_wind;
        }
        else if (weatherId == 800) {
            forecastIconId = R.drawable.ic_sun;
        }
        else if (weatherId < 900) {
            forecastIconId = R.drawable.ic_cloud;
        }
        else if (weatherId < 950) {
            forecastIconId = R.drawable.ic_tornado;
        }
        else {
            forecastIconId = R.drawable.ic_sun;
        }

        return forecastIconId;
    }
}
