package za.co.hawkiesza.openweathermapdemo.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import za.co.hawkiesza.openweathermapdemo.OpenWeatherMapApp;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        CurrentWeatherActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(OpenWeatherMapApp openWeatherMapApp);
}
