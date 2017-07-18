package za.co.hawkiesza.openweathermapdemo;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import za.co.hawkiesza.openweathermapdemo.response.WeatherResponse;

public class CurrentWeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 3;
    private OpenWeatherMapService service;
    private TextView currentTemperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private TextView currentPlaceTextView;
    private TextView infoTextView;
    private ProgressBar progressBar;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.current_weather);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startLocationUpdates();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OpenWeatherMapService.class);

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
    public void onResume()
    {
        super.onResume();
        if (googleApiClient.isConnected())
        {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onStart()
    {
        googleApiClient.connect();
        super.onStart();
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

        result.setResultCallback(new ResultCallback<LocationSettingsResult>()
        {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult)
            {
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
            service.getCurrentWeatherInfo(currentLocation.getLatitude(), currentLocation.getLongitude(), getString(R.string.API_KEY), "metric").enqueue(new Callback<WeatherResponse>()
            {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
                {
                    currentPlaceTextView.setText(response.body().getCityName());
                    currentTemperatureTextView.setText(String.format(getString(R.string.display_temperature), response.body().getMain().getTemp()));
                    minTemperatureTextView.setText(String.format(getString(R.string.display_temperature), response.body().getMain().getTempMin()));
                    maxTemperatureTextView.setText(String.format(getString(R.string.display_temperature), response.body().getMain().getTempMax()));

                    infoTextView.animate().alpha(0.0f);
                    progressBar.animate().alpha(0.0f);
                    infoTextView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    stopLocationUpdates();
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t)
                {
                    Toast.makeText(CurrentWeatherActivity.this, R.string.generic_api_error, Toast.LENGTH_LONG).show();
                }
            });
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
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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
}
