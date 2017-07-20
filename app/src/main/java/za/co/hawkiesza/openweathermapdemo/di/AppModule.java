package za.co.hawkiesza.openweathermapdemo.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import za.co.hawkiesza.openweathermapdemo.OpenWeatherMapService;
import za.co.hawkiesza.openweathermapdemo.R;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    OpenWeatherMapService provideOpenWeatherMapService(Application app) {
        return new Retrofit.Builder()
                .baseUrl(app.getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapService.class);
    }
}
