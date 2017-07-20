package za.co.hawkiesza.openweathermapdemo.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import za.co.hawkiesza.openweathermapdemo.WeatherRepository;
import za.co.hawkiesza.openweathermapdemo.ui.CurrentWeatherViewModel;

@Singleton
public class CurrentWeatherViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final WeatherRepository weatherRepository;

    @Inject
    public CurrentWeatherViewModelFactory(Application application, WeatherRepository weatherRepository) {
        this.application = application;
        this.weatherRepository = weatherRepository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CurrentWeatherViewModel create(Class modelClass) {
        return new CurrentWeatherViewModel(application, weatherRepository);
    }
}
