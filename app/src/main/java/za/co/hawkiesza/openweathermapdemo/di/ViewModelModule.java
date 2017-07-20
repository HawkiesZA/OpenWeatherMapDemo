package za.co.hawkiesza.openweathermapdemo.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import za.co.hawkiesza.openweathermapdemo.ui.CurrentWeatherViewModel;

@Module
abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CurrentWeatherViewModelFactory factory);
}