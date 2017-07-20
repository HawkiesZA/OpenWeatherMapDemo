package za.co.hawkiesza.openweathermapdemo.ui;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import za.co.hawkiesza.openweathermapdemo.R;
import za.co.hawkiesza.openweathermapdemo.di.CurrentWeatherViewModelFactory;

public class CurrentWeatherActivity extends LifecycleActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, HasActivityInjector
{
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 3;
    private TextView currentTemperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private TextView currentPlaceTextView;
    private TextView infoTextView;
    private ProgressBar progressBar;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private CurrentWeatherViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    CurrentWeatherViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel.class);
        viewModel.getWeather().observe(this, weatherResponse -> {
            if (weatherResponse != null) {
                currentPlaceTextView.setText(weatherResponse.getCityName());
                currentTemperatureTextView.setText(String.format(getString(R.string.display_temperature), weatherResponse.getMain().getTemp()));
                minTemperatureTextView.setText(String.format(getString(R.string.display_temperature), weatherResponse.getMain().getTempMin()));
                maxTemperatureTextView.setText(String.format(getString(R.string.display_temperature), weatherResponse.getMain().getTempMax()));

                infoTextView.animate().alpha(0.0f);
                progressBar.animate().alpha(0.0f);
                infoTextView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            else
            {
                Toast.makeText(CurrentWeatherActivity.this, R.string.generic_api_error, Toast.LENGTH_LONG).show();
            }

            stopLocationUpdates();
        });

        setContentView(R.layout.activity_current_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.current_weather);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> startLocationUpdates());

        currentTemperatureTextView = (TextView) findViewById(R.id.current_temperature);
        minTemperatureTextView = (TextView) findViewById(R.id.min_temperature);
        maxTemperatureTextView = (TextView) findViewById(R.id.max_temperature);
        currentPlaceTextView = (TextView) findViewById(R.id.place);
        infoTextView = (TextView) findViewById(R.id.info_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (googleApiClient == null)
        {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        createLocationRequest();

        checkLocationSettings();
    }

    @Override
    protected void onStart()
    {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onResume()
    {
        if (googleApiClient.isConnected())
        {
            startLocationUpdates();
        }
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        stopLocationUpdates();
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_CHECK_SETTINGS:
            {
                if (resultCode == RESULT_OK)
                {
                    startLocationUpdates();
                }
                break;
            }
        }
    }

    protected void createLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkLocationSettings()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(locationSettingsResult -> {
            final Status status = locationSettingsResult.getStatus();
            switch (status.getStatusCode())
            {
                case LocationSettingsStatusCodes.SUCCESS:
                {
                    // All location settings are satisfied. The client can
                    // initialize location requests here.
                    break;
                }
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try
                    {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(CurrentWeatherActivity.this, REQUEST_CHECK_SETTINGS);
                    }
                    catch (IntentSender.SendIntentException e)
                    {
                        // Ignore the error.
                    }
                    break;
                }
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                {
                    // Location settings are not satisfied. However, we have no way
                    // to fix the settings so we won't show the dialog.
                    break;
                }
            }
        });
    }

    private void refresh()
    {
        currentPlaceTextView.setText("");
        currentTemperatureTextView.setText("");
        minTemperatureTextView.setText("");
        maxTemperatureTextView.setText("");
        infoTextView.setText(R.string.getting_weather_info);

        if (currentLocation != null)
        {
            viewModel.setLocation(currentLocation);
        }
    }

    protected void startLocationUpdates()
    {
        infoTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        infoTextView.animate().alpha(1.0f);
        progressBar.animate().alpha(1.0f);
        infoTextView.setText(R.string.getting_location);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
        }
        else
        {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    protected void stopLocationUpdates()
    {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_ACCESS_FINE_LOCATION:
            case REQUEST_ACCESS_COARSE_LOCATION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    startLocationUpdates();
                }
                break;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        startLocationUpdates();
        refresh();
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        currentLocation = location;
        refresh();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
