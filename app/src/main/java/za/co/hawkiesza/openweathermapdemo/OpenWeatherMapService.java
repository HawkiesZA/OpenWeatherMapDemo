package za.co.hawkiesza.openweathermapdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import za.co.hawkiesza.openweathermapdemo.response.WeatherResponse;

public interface OpenWeatherMapService {

    @GET("weather")
    Call<WeatherResponse> getCurrentWeatherInfo(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String apiKey, @Query("units") String units);
}
