package za.co.hawkiesza.openweathermapdemo.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import dagger.android.AndroidInjection;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import za.co.hawkiesza.openweathermapdemo.OpenWeatherMapApp;

public class AppInjector {
    private AppInjector() {}
    public static void init(OpenWeatherMapApp openWeatherMapApp) {
        DaggerAppComponent.builder().application(openWeatherMapApp)
                .build().inject(openWeatherMapApp);
        openWeatherMapApp
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasActivityInjector) {
            AndroidInjection.inject(activity);
        }
    }
}
