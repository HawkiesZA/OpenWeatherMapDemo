package za.co.hawkiesza.openweathermapdemo.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.location.Location;

import java.util.Objects;

import javax.inject.Inject;

import za.co.hawkiesza.openweathermapdemo.R;
import za.co.hawkiesza.openweathermapdemo.WeatherRepository;
import za.co.hawkiesza.openweathermapdemo.response.ForecastResponse;
import za.co.hawkiesza.openweathermapdemo.response.WeatherResponse;

public class CurrentWeatherViewModel extends AndroidViewModel {

    private MutableLiveData<Location> location = new MutableLiveData<>();
    private LiveData<WeatherResponse> weather;
    private LiveData<ForecastResponse> forecast;

    @Inject
    public CurrentWeatherViewModel(Application application, WeatherRepository weatherRepository)
    {
        super(application);
        //this.weather = Transformations.switchMap(location, location -> weatherRepo.getWeather(this.getApplication().getString(R.string.API_KEY), location));
        this.forecast = Transformations.switchMap(location, location -> weatherRepository.getForecast(this.getApplication().getString(R.string.API_KEY), location));
    }

    public void setLocation(Location location) {
        if (Objects.equals(this.location.getValue(), location)) {
            return;
        }
        this.location.setValue(location);
    }

    public LiveData<WeatherResponse> getWeather() {
        return weather;
    }

    public LiveData<ForecastResponse> getForecast() {
        return forecast;
    }
}
