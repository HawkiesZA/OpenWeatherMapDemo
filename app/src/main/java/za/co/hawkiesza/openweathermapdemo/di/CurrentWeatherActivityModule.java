package za.co.hawkiesza.openweathermapdemo.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.hawkiesza.openweathermapdemo.ui.CurrentWeatherActivity;

@Module
public abstract class CurrentWeatherActivityModule {
    @ContributesAndroidInjector
    abstract CurrentWeatherActivity contributeCurrentWeatherActivity();
}
