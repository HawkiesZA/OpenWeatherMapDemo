package za.co.hawkiesza.openweathermapdemo;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.hawkiesza.openweathermapdemo.response.ForecastResponse;
import za.co.hawkiesza.openweathermapdemo.response.WeatherResponse;

public class WeatherRepository {
    private OpenWeatherMapService service;

    @Inject
    public WeatherRepository(OpenWeatherMapService service)
    {
       this.service = service;
    }

    public LiveData<WeatherResponse> getWeather(String apiKey, Location currentLocation)
    {
        final MutableLiveData<WeatherResponse> weatherData = new MutableLiveData<>();
        if (currentLocation != null)
        {
            service.getCurrentWeatherInfo(currentLocation.getLatitude(), currentLocation.getLongitude(), apiKey, "metric").enqueue(new Callback<WeatherResponse>()
            {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
                {
                    weatherData.setValue(response.body());
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t)
                {
                    //Toast.makeText(CurrentWeatherActivity.this, R.string.generic_api_error, Toast.LENGTH_LONG).show();
                }
            });
        }

        return weatherData;
    }

    public LiveData<ForecastResponse> getForecast(String apiKey, Location currentLocation)
    {
        final MutableLiveData<ForecastResponse> forecastData = new MutableLiveData<>();
        if (currentLocation != null)
        {
            service.getCurrentForecast(currentLocation.getLatitude(), currentLocation.getLongitude(), apiKey, "metric").enqueue(new Callback<ForecastResponse>()
            {
                @Override
                public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response)
                {
                    Log.d("WeatherRepository", new Gson().toJson(response));
                    forecastData.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ForecastResponse> call, Throwable t)
                {
                    //Toast.makeText(CurrentWeatherActivity.this, R.string.generic_api_error, Toast.LENGTH_LONG).show();
                }
            });
        }

        return forecastData;
    }
}
